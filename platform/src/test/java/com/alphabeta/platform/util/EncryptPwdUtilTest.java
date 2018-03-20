package com.alphabeta.platform.util;

import org.junit.Test;

public class EncryptPwdUtilTest {

    @Test
    public void encryptWithoutSalt() {
        String password = "123456";
        EncryptPwdUtil.Encrypt encrypt = EncryptPwdUtil.encrypt(password);
        System.out.println(encrypt.getEncrypt());
        System.out.println(encrypt.getSalt());
    }

    @Test
    public void encryptWithSalt() {
        String password = "123456";
        String salt = "kNuax0Qv+oC5HNCa4vx6MQ==";
        EncryptPwdUtil.Encrypt encrypt = EncryptPwdUtil.encrypt(password, salt);
        System.out.println(encrypt.getEncrypt());
        System.out.println(encrypt.getSalt());
    }
}
