package design.first.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import design.first.commons.tools.MD5;
import design.first.commons.tools.RSACoder;
import design.first.user.config.StaticReidsPrefix;
import design.first.user.utils.ImagevalidateCode;
import design.first.user.utils.TmpMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "登录模块")
@RestController
@RequestMapping(value = "login")
public class LoginController {
    private static Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Resource
    private TmpMap tmpMap;

    @Resource
    private DefaultKaptcha captchaProducer;

    @Resource(name="stringRedisTemplate")
    private StringRedisTemplate ops;

    @ApiOperation(value = "获取公钥",notes = "每次获取公钥时都将生成密钥对")
    @GetMapping(value = "getPublicKey")
    public String getPublicKey() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();
        //公钥
        byte[] publicKey =RSACoder.getPublicKey(keyMap);
        //私钥
        byte[] privateKey = RSACoder.getPrivateKey(keyMap);
        tmpMap.setPrivateKey(privateKey);
        tmpMap.setPublicKey(publicKey);
        String ret=Base64.encodeBase64String(publicKey);
        logger.info("base64公钥:{}",ret);
        //生成唯一key返给用户，确保多用户场景可用
        Map<String,String> map=new LinkedHashMap<>();
        map.put(StaticReidsPrefix.PUBLIC_KEY,Base64.encodeBase64String(publicKey));
        map.put(StaticReidsPrefix.PRIVATE_KEY,Base64.encodeBase64String(privateKey));
        String key=StaticReidsPrefix.RSA_PUBLICKEY_PRE+ MD5.MD5Encode(ret);
        ops.opsForValue().set(key,JSON.toJSONString(map),10, TimeUnit.MINUTES);//缓存
        return ret;
    }

    /**
     * @param name 用户名
     * @param password 密码
     * @param key
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "密码登录",notes = "密码登录")
    @GetMapping(value = "loginByPassword")
    public String loginByPassword(String name,String password,String key) throws Exception {
        logger.info("base64密码:{}",password);
        //解码
        byte[] pwd = Base64.decodeBase64(password);
        //私钥解密
        key = ops.opsForValue().get(StaticReidsPrefix.RSA_PUBLICKEY_PRE+ MD5.MD5Encode(key));
        JSONObject jsonObject = JSONObject.parseObject(key);
        String privateKey = jsonObject.getString(StaticReidsPrefix.PRIVATE_KEY);

        byte[] bytes = RSACoder.decryptByPrivateKey(pwd, Base64.decodeBase64(privateKey));
        //前端输入的密码
        String pwdString=new String(bytes);
        logger.info("前端输入的密码:{}",pwdString);

        //数据库用户名密码验证
        return pwdString;
    }

    @ApiOperation(value = "获取验证码",notes = "获取验证码")
    @GetMapping(value = "/getValidateCode")
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImagevalidateCode.validateCode(request,response,captchaProducer,"wq");
    }
    @ApiOperation(value = "获取JSON验证码",notes = "获取JSON验证码,data:image/jpeg;base64,")
    @GetMapping(value = "/getJSONValidateCode")
    public Map<String,String> getJSONValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ImagevalidateCode.JOSNvalidateCode(captchaProducer);
    }

}
