package com.alphabeta.platform.core.web.exception;

import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.util.CodeUtil;
import com.alphabeta.platform.core.util.ExceptionUtil;
import com.alphabeta.platform.core.util.I18NUtil;
import com.alphabeta.platform.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.alphabeta.platform.core.common.ErrorCode.ERROR_INVALID_REQUEST;
import static com.alphabeta.platform.core.common.ErrorCode.ERROR_SYS_EXCEPTION;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${sys.ignore.errorMsg}")
    private boolean ignoreErrorMsg;

    @ResponseBody
    @ExceptionHandler(value = BaseAppException.class)
    public ResponseEntity<BaseResult> handleBaseAppException(HttpServletRequest request, HttpServletResponse response, BaseAppException e) {
        ExceptionUtil.logError(logger, "BaseApp exceptions!!!", e);
        return new ResponseEntity<BaseResult>(buildBaseResult(e.getCode(), e.getDesc(), false), HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<BaseResult> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException be) {
        ExceptionUtil.logError(logger, "HttpMediaTypeNotAcceptable exceptions!!!", be);
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, be.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResult> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException be) {
        ExceptionUtil.logError(logger, "HttpRequestMethodNotSupported exceptions!!!", be);
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, be.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException be) {
        ExceptionUtil.logError(logger, "NoHandlerFound exceptions!!!", be);
        return new ResponseEntity<>(buildBaseResult(ERROR_SYS_EXCEPTION, be.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResult> handleBindException(BindException e) {
        ExceptionUtil.logError(logger, "Bind exceptions!!!", e);
        List<ObjectError> errors = e.getAllErrors();
        try {
            Assert.notEmpty(errors, null);
        } catch (IllegalArgumentException ie) {
            return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, ie.getMessage()), HttpStatus.BAD_REQUEST);
        }
        ObjectError error = e.getAllErrors().get(0);
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, error.getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
        MissingServletRequestParameterException.class, MissingServletRequestPartException.class,
        TypeMismatchException.class, ServletRequestBindingException.class})
    public ResponseEntity<BaseResult> handleExceptions(Exception e) {
        ExceptionUtil.logError(logger, "BadRequest exceptions!!!", e);
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_INVALID_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = {ConversionNotSupportedException.class, HttpMessageNotWritableException.class, Exception.class})
    public ResponseEntity<BaseResult> handleException(Exception e) {
        ExceptionUtil.logError(logger, "Unexpected exceptions!!!", e);
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 默认忽略 errorMsg
     * @param errorCode
     * @param errorMsg
     * @return
     */
    private BaseResult buildBaseResult(Enum<?> errorCode, String errorMsg) {
        return this.buildBaseResult(errorCode, errorMsg, true);
    }

    /**
     * @param errorCode
     * @param errorMsg
     * @param ignore 是否忽略 true:忽略；false:不忽略
     * @return
     */
    private BaseResult buildBaseResult(Enum<?> errorCode, String errorMsg, boolean ignore) {
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return this.buildBaseResult(errorCodeString, errorMsg, ignore);
    }

    /**
     * 默认忽略 errorMsg
     * @param errorCode
     * @param errorMsg
     * @return
     */
    private BaseResult buildBaseResult(String errorCode, String errorMsg) {
        return buildBaseResult(errorCode, errorMsg, true);
    }

    /**
     *
     * @param errorCode
     * @param errorMsg
     * @param ignore 是否忽略 true:忽略；false:不忽略
     * @return
     */
    private BaseResult buildBaseResult(String errorCode, String errorMsg, boolean ignore) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);

        // 是否忽略errorMsg，如果开启的全局开关，则忽略，全局开关默认true，即忽略非BaseAppException外的errorMsg
        if((ignoreErrorMsg || !ignore) && ObjectUtil.isNotNull(errorMsg)) {
            result.setErrorMessage(errorMsg);
        } else {
            String errorMessage = I18NUtil.getMessage(errorCode);
            result.setErrorMessage(errorMessage);
        }
        return result;
    }


}
