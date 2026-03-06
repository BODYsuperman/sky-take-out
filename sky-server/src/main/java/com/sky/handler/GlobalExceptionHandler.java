package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    // 捕获唯一键冲突异常
    @ExceptionHandler
    public Result handleDuplicateKeyException(SQLIntegrityConstraintViolationException  e) {
        log.error("唯一键冲突：{}", e.getMessage());
        // 提取冲突字段（简化版，可根据实际日志优化）

        String message = e.getMessage();


        log.error(e.getMessage()+ "22222222222222222222222222222222222222");
        if(message.contains("Duplicate")){

            String[] split = message.split(" ");
            String msge = split[2] + MessageConstant.EMPLOYEE_USERNAME_EXIST;
            return Result.error(msge);
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);

    }

}
