package com.alphabeta.platform.core.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回请求参数基类
 *
 * @author deng.qiming
 * @date 2016年11月8日 上午10:49:47
 */
public class BaseParam {
    private String sign;
    private Map params;

    public BaseParam() {
        this.params = new HashMap();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }
}
