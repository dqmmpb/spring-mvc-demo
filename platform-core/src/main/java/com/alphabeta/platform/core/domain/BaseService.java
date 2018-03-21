package com.alphabeta.platform.core.domain;

import com.alphabeta.platform.core.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象公共Service
 *
 * @author deng.qiming
 * @date 2016-12-22 15:02
 */
public abstract class BaseService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 使用盐值加密密码，如没有传递盐值，则系统自动生成一个盐值
     *
     * @param password
     * @param salt
     * @return
     */
    protected EncryptUtil.Encrypt getEncryptPwd(String password, String salt) {
        if (StringUtils.isNotEmpty(salt)) {
            return EncryptUtil.encrypt(password, salt);
        } else {
            return EncryptUtil.encrypt(password);
        }
    }
}
