package com.alphabeta.platform.base.exception;

import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.util.ExceptionUtil;
import com.alphabeta.platform.core.util.I18NUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
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
import java.lang.reflect.Method;
import java.util.List;

import static com.alphabeta.platform.core.common.ErrorCode.ERROR_INVALID_REQUEST;
import static com.alphabeta.platform.core.common.ErrorCode.ERROR_SYS_EXCEPTION;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = BaseAppException.class)
    public ResponseEntity<BaseResult> handleBaseAppException(HttpServletRequest request, HttpServletResponse response, BaseAppException e) {
        return new ResponseEntity<BaseResult>(buildBaseResult(e.getCode(), e.getDesc()),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<BaseResult> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException be) {
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, be.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResult> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException be) {
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, be.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException be) {
        return new ResponseEntity<>(be.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResult> handleBindException(BindException e) {
        List<ObjectError> errors = e.getAllErrors();
        try {
            Assert.notEmpty(errors, ERROR_SYS_EXCEPTION.getCodeString());
        } catch (IllegalArgumentException ie) {
            return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, null), HttpStatus.BAD_REQUEST);
        }
        ObjectError error = e.getAllErrors().get(0);
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, error.getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
        MissingServletRequestParameterException.class, MissingServletRequestPartException.class,
        TypeMismatchException.class, ServletRequestBindingException.class})
    public ResponseEntity<BaseResult> handleExceptions(Exception e) {
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_INVALID_REQUEST, e.getMessage()),
            HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = {ConversionNotSupportedException.class, HttpMessageNotWritableException.class, Exception.class})
    public ResponseEntity<BaseResult> handleException(Exception e) {
        ExceptionUtil.logError(logger, "Unexpected exceptions!!!", e);
        return new ResponseEntity<BaseResult>(buildBaseResult(ERROR_SYS_EXCEPTION, null),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private BaseResult buildBaseResult(Enum<?> errorCode, String errorMsg) {
        try {
            Method method = errorCode.getClass().getMethod("getCodeString");
            Object errorCodeString = method.invoke(errorCode);
            return this.buildBaseResult((String) errorCodeString, errorMsg);
        } catch (Exception e) {
            try {
                Method method = errorCode.getClass().getMethod("getCode");
                Object errorCodeInteger = method.invoke(errorCode);
                return this.buildBaseResult("" + errorCodeInteger, errorMsg);
            } catch (Exception ie) {
                return this.buildBaseResult(ERROR_SYS_EXCEPTION, errorMsg);
            }
        }
    }

    private BaseResult buildBaseResult(String errorCode, String errorMsg) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        if (errorMsg == null) {
            String errorMessage = I18NUtil.getMessage(errorCode);
            result.setErrorMessage(errorMessage);
        } else {
            result.setErrorMessage(errorMsg);
        }
        return result;
    }

}
