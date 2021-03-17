package design.first.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import design.first.commons.tools.MD5;
import design.first.commons.tools.RSACoder;
import design.first.user.base.BaseResult;
import design.first.user.config.StaticReidsPrefix;
import design.first.user.entity.UserInfo;
import design.first.user.service.UserInfoService;
import design.first.user.utils.ImagevalidateCode;
import design.first.user.utils.TmpMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "登录模块")
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private TmpMap tmpMap;
    @Resource
    private UserInfoService userInfoService;

    @Resource(name = "taskExe")
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private DefaultKaptcha captchaProducer;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate ops;

    @ApiOperation(value = "获取公钥", notes = "每次获取公钥时都将生成密钥对")
    @GetMapping(value = "getPublicKey")
    public String getPublicKey() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();
        //公钥
        byte[] publicKey = RSACoder.getPublicKey(keyMap);
        //私钥
        byte[] privateKey = RSACoder.getPrivateKey(keyMap);
        String ret = Base64.encodeBase64String(publicKey);
        logger.info("base64公钥:{}", ret);
        //生成唯一key返给用户，确保多用户场景可用
        Map<String, String> map = new LinkedHashMap<>();
        map.put(StaticReidsPrefix.PUBLIC_KEY, Base64.encodeBase64String(publicKey));
        map.put(StaticReidsPrefix.PRIVATE_KEY, Base64.encodeBase64String(privateKey));
        String key = StaticReidsPrefix.RSA_PUBLICKEY_PRE + MD5.MD5Encode(ret);
        try {
            ops.opsForValue().set(key, JSON.toJSONString(map), 10, TimeUnit.MINUTES);//缓存10分钟
        } catch (Exception e) {
            tmpMap.setPrivateKey(privateKey);
            tmpMap.setPublicKey(publicKey);
            logger.info("redis服务异常，使用对象缓存密钥，不支持多用户操作");
        }
        return ret;
    }

    /**
     * @param name     用户名
     * @param password 密码
     * @param key      公钥
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "密码登录", notes = "密码登录")
    @PostMapping(value = "loginByPassword")
    public BaseResult loginByPassword(String name, String password, String key) throws Exception {
        logger.info("base64密码:{}", password);
        //解码
        byte[] pwd = Base64.decodeBase64(password);
        byte[] bytes;//真实密码字节码
        byte[] privateKeyByte;//私钥字节码
        //私钥解密
        try {
            key = ops.opsForValue().get(StaticReidsPrefix.RSA_PUBLICKEY_PRE + MD5.MD5Encode(key));
            logger.info("该密钥不存在/已过期，请重新获取");
            JSONObject jsonObject = JSONObject.parseObject(key);//取得密钥对
            String privateKey = jsonObject.getString(StaticReidsPrefix.PRIVATE_KEY);
            privateKeyByte=Base64.decodeBase64(privateKey);
        } catch (Exception e) {
            logger.info("redis服务异常，使用缓存对象获取私钥");
            privateKeyByte=tmpMap.getPrivateKey();
        }
        bytes = RSACoder.decryptByPrivateKey(pwd, privateKeyByte);
        //前端输入的密码
        String pwdString = new String(bytes);
        logger.info("前端输入的密码:{}", pwdString);

        //shiro 验证
        //创建登录主体 注意：此时主体没有经过验证，仅仅是个空的对象
        Subject subject = SecurityUtils.getSubject();
        //绑定主体登陆的身份、凭证 即账号密码
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwdString);
        //主体登陆
        subject.login(token);
        //登陆判断
        if (subject.isAuthenticated()) {
            //异步更新登录时间
            taskExecutor.execute(() -> {
                UserInfo userInfo = JSONObject.parseObject(JSON.toJSONString(subject.getPrincipal()), UserInfo.class);
                Long no = userInfo.getUserNo();
                userInfo = new UserInfo();
                userInfo.setUserNo(no);
                userInfo.setLastLoginTime(LocalDateTime.now());
                userInfoService.updateByUserNo(userInfo);
            });
        }
        return BaseResult.success("登录成功", subject.getPrincipal());
    }

    @ApiOperation("当前登录用户")
    @GetMapping("/actionUser")
    public BaseResult actionUser() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return BaseResult.success("已登录用户", subject.getPrincipal());
        }
        return BaseResult.success("用户未登录");
    }

    @ApiOperation(value = "shiro密码登录测试", notes = "密码登录测试")
    @PostMapping(value = "loginByPasswordTest")
    public BaseResult loginByPasswordTest(String name, String password) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        //绑定主体登陆的身份、凭证 即账号密码
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //主体登陆
        subject.login(token);
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return BaseResult.success(user);
    }

    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @GetMapping(value = "/getValidateCode")
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImagevalidateCode.validateCode(request, response, captchaProducer, "wq");
    }

    @ApiOperation(value = "获取JSON验证码", notes = "获取JSON验证码,data:image/jpeg;base64,")
    @GetMapping(value = "/getJSONValidateCode")
    public Map<String, String> getJSONValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ImagevalidateCode.JOSNvalidateCode(captchaProducer);
    }

    @GetMapping("/loginOut")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public BaseResult loginOut() {
        UserInfo user = new UserInfo();
        Subject subject = SecurityUtils.getSubject();
        Object obj = subject.getPrincipal();
        subject.logout();
        BeanUtils.copyProperties(obj, user);
        return BaseResult.success();
    }

}