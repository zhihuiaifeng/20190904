package com_xzyh_crm.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com_xzyh_crm.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Component
@Slf4j
public class CustomExceptionHandler {
	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> errorHandler(Exception ex) {
        ex.printStackTrace();
        if(ex instanceof CustomException){
            CustomException customException=(CustomException) ex;
            log.error("errorLog=={}",getStackTrace(ex));
            return new ResponseEntity<>(new Result(customException.getCode(),customException.getMsg()),HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            log.error("errorLog=={}",getStackTrace(ex));
            return new ResponseEntity<>(new Result("500","发生异常，请联系客服"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     * 和上面的异常配合使用的,前端的异常就是这个
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return Result.resultData("408", result.get("errorMsg").toString());
    }
    /**
     * 获取校验错误信息
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        List<String> errorList = new ArrayList<String>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }
    /*捕获了传入参数类型错误的异常*/
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result> MethodTypeerrorHandler(Exception ex) {
        ex.printStackTrace();
        if(ex instanceof CustomException){
            CustomException customException=(CustomException) ex;
            log.error("errorLog=={}",getStackTrace(ex));
            return new ResponseEntity<>(new Result(customException.getCode(),customException.getMsg()),HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            log.error("errorLog=={}",getStackTrace(ex));
            return new ResponseEntity<>(new Result("400","传入的参数不符合规范"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	/**
     * 完整的堆栈信息
     *
     * @param e Exception
     * @return Full StackTrace
     */
    private  static String getStackTrace(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
