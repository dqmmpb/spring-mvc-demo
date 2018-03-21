package com.alphabeta.platform.core.common.alg;

public interface Algorithm {

    String encrypt(String password, String... salts);

    Algorithm APPEND = new AppendAlgorithm();
}
