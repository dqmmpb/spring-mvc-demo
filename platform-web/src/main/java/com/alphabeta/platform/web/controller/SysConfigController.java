package com.alphabeta.platform.web.controller;

import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lelia
 * Datetime: 2018/3/9 14:35
 * Description:
 */
@RestController
@RequestMapping("/v1/sys")
public class SysConfigController extends BaseController {

    @Value("${rsa.publicKey}")
    private String publicKey;

    /**
     * 获取系统配置信息接口
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public BaseResult config(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        BaseResult result = new BaseResult();

        HashMap<String, Object> map = new HashMap<>();

        if (ObjectUtil.isNotNull(publicKey)) {
            map.put("publicKey", publicKey); // 公钥
        }

        result.setResult(map);
        return result;
    }
}
