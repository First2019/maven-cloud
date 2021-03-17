package design.first.user.controller;

import design.first.commons.tools.RSACoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "加解密通道")
@RestController
@RequestMapping(value = "/rsa")
public class EncryptedTunnelController {
    static Logger logger= LoggerFactory.getLogger(EncryptedTunnelController.class);

    @ApiOperation(value = "私钥加密",notes = "私钥加密,加密后用base64编码")
    @GetMapping(value = "encryptByPrivateKey")
    public String encryptByPrivateKey(String data, String key) throws Exception {
        //解码输入值并加密
        byte[] bytes = RSACoder.encryptByPrivateKey(data.getBytes(), Base64.decodeBase64(key));
        //返回base64编码值
        String ret=Base64.encodeBase64String(bytes);
        logger.info("base64密码编码值:{}",ret);
        return ret;
    }
    @ApiOperation(value = "公钥加密",notes = "公钥加密,加密后用base64编码")
    @GetMapping(value = "encryptByPublicKey")
    public String encryptByPublicKey(String data, String key) throws Exception {
        byte[] bytes = RSACoder.encryptByPublicKey(data.getBytes(), Base64.decodeBase64(key));
        String ret=Base64.encodeBase64String(bytes);
        logger.info("base64密码编码值:{}",ret);
        return ret;
    }
}
