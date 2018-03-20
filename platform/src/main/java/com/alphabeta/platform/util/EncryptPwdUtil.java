package com.alphabeta.platform.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 密码验证，加盐
 *
 * @author deng.qiming
 * @date 2017-01-10 17:46
 */
public class EncryptPwdUtil {

    public final static Random RANDOM = new SecureRandom();
    public final static int SALT_LENGTH = 16;

    /**
     * 加密对象
     */
    public static class Encrypt {
        private String salt; // salt
        private String encrypt; // 加密后对象

        public Encrypt() {

        }

        public Encrypt(String salt, String encrypt) {
            this.salt = salt;
            this.encrypt = encrypt;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getEncrypt() {
            return encrypt;
        }

        public void setEncrypt(String encrypt) {
            this.encrypt = encrypt;
        }
    }

    /**
     * 生成加密字符串
     *
     * @param password
     * @param salt
     * @return
     */
    public final static String encryptRaw(String password, String salt) {
        StringBuilder sb = new StringBuilder(password);
        sb.append("#").append(password).append('#').append(salt);
        String encrypt = DigestUtils.md5Hex(sb.toString());
        return encrypt;
    }

    /**
     * 生成加密对象
     *
     * @param password
     * @param salt
     * @return
     */
    public final static Encrypt encrypt(String password, String salt) {
        String encrypt = encryptRaw(password, salt);
        return new Encrypt(salt, encrypt);
    }

    /**
     * 生成加密对象
     *
     * @param password
     * @return
     */
    public final static Encrypt encrypt(String password) {
        String salt = salt();
        return encrypt(password, salt);
    }

    /**
     * 生成salt
     *
     * @return
     */
    private final static String salt() {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return Base64.encodeBase64String(salt);
    }

}
