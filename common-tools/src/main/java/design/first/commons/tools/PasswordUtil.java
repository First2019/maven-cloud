package design.first.commons.tools;

/**
 * ASCLL-->
 * 48---->0
 * 57---->9
 * 65-90------>A--Z
 * 97-122----->a--z
 * 共62个字符
 */

public class PasswordUtil {

    public static void main(String[] args){
        System.out.println(getSalt(10));
    }
    /**
     * 差生长度为count的密码盐
     * @param count
     * @return
     */
    public static String getSalt(int count){
        return RandomUtil.code(count).toUpperCase();
    }

    /**
     * 加密，产生新密码
     * @param origin
     * @return
     */
    public static String encrypt(String origin){
        return MD5.MD5Encode(origin);
    }

    /**
     * 密码验证
     * @param inPwd 前端实际输入值
     * @param rePwd 数据库密码
     * @param salt 盐值
     * @return
     */
    public boolean pwdMatch(String userName,String inPwd,String rePwd,String salt){
        String md5Encode = MD5.MD5Encode(userName + inPwd + salt);
        return md5Encode.equals(rePwd);
    }

}
