package com.alphabeta.platform.web.controller;

import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.util.ObjectUtil;
import com.alphabeta.platform.core.util.RSAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import static com.alphabeta.platform.web.common.ErrorCode.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lelia
 * Datetime: 2018/3/9 14:35
 * Description:
 */
@RestController
@RequestMapping("/v1/sys/tools")
public class SysToolsController extends BaseController {

    @Value("${rsa.privateKey}")
    private String privateKeyStr;

    @Value("${rsa.publicKey}")
    private String publicKeyStr;

    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    public BaseResult encrypt(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        BaseResult result = new BaseResult();

        if (ObjectUtil.isNull(params.get("encryptStr"))) {
            ExceptionHandler.publish(INVALID_PARAMS_ERROR);
        }

        String encryptStr = null;

        try {
            encryptStr = (String) params.get("encryptStr");
        } catch (Exception e) {
            ExceptionHandler.publish(INVALID_PARAMS_FORMAT_ERROR);
        }

        String encryptResult = null;
        try {
            PublicKey publicKey = RSAUtil.getPublicRSAKey(publicKeyStr);
            encryptResult = RSAUtil.encryptToString(encryptStr, publicKey);
        } catch (Exception e) {
            ExceptionHandler.publish(ENCRYPT_ERROR);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("encryptResult", encryptResult);

        result.setResult(map);
        return result;
    }
}
