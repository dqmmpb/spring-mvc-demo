package com.alphabeta.platform.web.controller;

import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.PageParam;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.util.ObjectUtil;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.alphabeta.platform.web.common.ErrorCode.PARAMS_SIGN_FAILED_ERROR;

/**
 * 项目基类
 *
 * @author deng.qiming
 * @date 2016年11月8日 上午10:54:39
 */
public abstract class BaseController extends com.alphabeta.platform.base.domain.BaseController {

    @Resource
    protected RedisTemplate<String, String> redisTemplate;

    public Map parseParams(BaseParam param) throws BaseAppException {
        return parseParams(param, false);
    }

    public Map parseParams(BaseParam param, boolean validSign) throws BaseAppException {
        if (ObjectUtil.isNotNull(param)) {
            Map params = param.getParams();
            if (validSign && !this.validSign(param)) {
                ExceptionHandler.publish(PARAMS_SIGN_FAILED_ERROR);
            }
            if (ObjectUtil.isNotNull(params)) {
                return params;
            }
        }
        return new HashMap();
    }

    /**
     * 分页参数提取
     *
     * @param params
     * @return
     */
    protected PageParam parsePage(Map params) {
        if (ObjectUtil.isNotNull(params)) {
            if (ObjectUtil.isNotNull(params.get("pageNum")) && ObjectUtil.isNotNull(params.get("pageSize"))) {
                Integer pageNum = ((Number) params.get("pageNum")).intValue();
                Integer pageSize = ((Number) params.get("pageSize")).intValue();
                return new PageParam(pageNum, pageSize);
            } else if (ObjectUtil.isNotNull(params.get("pageNum"))) {
                Integer pageNum = ((Number) params.get("pageNum")).intValue();
                return new PageParam(pageNum);
            }
        }
        return new PageParam();
    }


    /**
     * 参数验签
     *
     * @param param
     * @return
     */
    private boolean validSign(BaseParam param) {
        // 提取签名
        if (ObjectUtil.isNotNull(param)) {
            String sign = param.getSign();
            if (ObjectUtil.isNotNull(sign)) {
                // TODO
                return true;
            }
        }

        return false;
    }
}
