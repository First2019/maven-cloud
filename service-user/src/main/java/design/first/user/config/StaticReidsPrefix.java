package design.first.user.config;

/**
 * 定义redis的key值前缀，减少key冲突发生可能性
 */
public class StaticReidsPrefix {
    public static String  RSA_PUBLICKEY_PRE="rsa_publicKey_prefix";
    public static String PUBLIC_KEY="publicKey";
    public static String PRIVATE_KEY="privateKey";
}
