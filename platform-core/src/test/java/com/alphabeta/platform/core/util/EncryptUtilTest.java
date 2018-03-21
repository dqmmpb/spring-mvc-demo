package com.alphabeta.platform.core.util;

import org.junit.Assert;
import org.junit.Test;

public class EncryptUtilTest {

    private static final String[][] USERS = {
        {"13819493700", "493700", "ZjvIrlAhFqNasRfpVzEX2w==", "18cfcfc1b2284684b571d9787562b7ae"},
        {"13819493701", "493701", "AEwWhiRUlNek/b6t2giVJw==", "692b05d0cac4fd9758ddaab1770f324b"},
        {"13819493702", "493702", "0jiFXFbLdNSRwgi2IUqbzg==", "5b04c903fa0652ed5dd7be731347e24a"},
    };

    @Test
    public void encryptWithoutSalt() {
        String password = "123456";
        EncryptUtil.Encrypt encrypt = EncryptUtil.encrypt(password);
        System.out.println(encrypt.getEncrypt());
        System.out.println(encrypt.getSalt());
    }


    @Test
    public void encryptWithSalt() {
        for (String[] user : USERS) {
            String password = user[1];
            String salt = user[2];
            EncryptUtil.Encrypt encrypt = EncryptUtil.encrypt(password, salt);
            System.out.println("salt: " + encrypt.getSalt() + ", encrypt: " + encrypt.getEncrypt());
            Assert.assertEquals(encrypt.getEncrypt(), user[3]);
        }

    }
}
