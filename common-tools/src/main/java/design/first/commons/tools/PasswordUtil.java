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
        String salt = getSalt(10);
        System.out.println("盐:"+salt);
        String encrypt = encrypt("admin", "password", salt);
        System.out.println("库密码:"+encrypt);
    }

    public static String getSalt(){
        return getSalt(10);
    }
    /**
     * 产生长度为count的密码盐
     * @param count
     * @return
     */
    public static String getSalt(int count){
        return RandomUtil.code(count).toUpperCase();
    }

    /**
     * 加密，产生新密码
     * @param account
     * @param pwd
     * @param salt
     * @return
     */
    public static String encrypt(String account,String pwd,String salt){
        return MD5.MD5Encode(account+pwd+salt);
    }

    /**
     * 密码验证
     * @param inPwd 前端实际输入值
     * @param rePwd 数据库密码
     * @param salt 盐值
     * @return
     */
    public static boolean pwdMatch(String account,String inPwd,String rePwd,String salt){
        String md5Encode = encrypt(account,inPwd,salt);
        return md5Encode.equals(rePwd);
    }

}
