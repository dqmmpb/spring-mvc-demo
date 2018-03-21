package com.alphabeta.platform.core.common.alg;

public class AppendAlgorithm implements Algorithm {

    @Override
    public String encrypt(String password, String... salts) {
        StringBuilder sb = new StringBuilder(password);
        for (String salt : salts) {
            sb.append("#").append(salt);
        }
        return sb.toString();
    }
}
