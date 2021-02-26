package design.first.user.utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import design.first.user.controller.LoginController;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图形验证码
 */
public class ImagevalidateCode {
    private static Logger logger= LoggerFactory.getLogger(ImagevalidateCode.class);

    public static void validateCode(HttpServletRequest request, HttpServletResponse response, DefaultKaptcha captchaProducer, String validateSessionKey) throws Exception{
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        request.getSession().setAttribute(validateSessionKey, capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 创建Base64验证码
     * @return
     */
    public static Map<String,String> JOSNvalidateCode(DefaultKaptcha captchaProducer) throws IOException {
        String capText = captchaProducer.createText();
        logger.info("生成验证码:{}",capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ImageIO.write(bi,"jpg",outputStream);
        byte[] bytes=outputStream.toByteArray();
        String s= Base64.encodeBase64String(bytes);
        Map<String,String> map=new LinkedHashMap<>();
        map.put("captcha",s);
        return map;
    }
}
