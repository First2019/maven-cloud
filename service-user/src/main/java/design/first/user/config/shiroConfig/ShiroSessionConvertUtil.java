package design.first.user.config.shiroConfig;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.*;

public class ShiroSessionConvertUtil {
    /**
     * 把session对象转化为byte数组
     * @param session
     * @return
     */
    public static byte[] sessionToByte(Session session){

        byte[] bytes = null;
        try(ByteArrayOutputStream bo = new ByteArrayOutputStream();
                ObjectOutputStream oo = new ObjectOutputStream(bo)){
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 把byte数组还原为session
     * @param bytes
     * @return
     */
    public static Session byteToSession(byte[] bytes){
        Session session = null;
        try(ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(bi)) {
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
    }

}
