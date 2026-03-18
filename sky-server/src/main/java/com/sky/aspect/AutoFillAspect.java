package com.sky.aspect;


import com.sky.anno.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
@Aspect
public class AutoFillAspect {

    @Pointcut("@annotation(com.sky.anno.AutoFill)")
    public void autoFillPointCut() {}


    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("自动填充公共字段");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }


        //first parameter is the entity object to be filled eg Employee, Dish, Setmeal etc
        Object entity = args[0];

        Long currentId = BaseContext.getCurrentId();

        // 4. 根据操作类型进行填充（使用反射）
        if (operationType == OperationType.INSERT) {
            // 插入：填充创建时间、更新时间、创建人
            setFieldValue(entity, AutoFillConstant.SET_CREATE_TIME, LocalDateTime.now());
            setFieldValue(entity, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.now());
            setFieldValue(entity, AutoFillConstant.SET_CREATE_USER, currentId);
            setFieldValue(entity, AutoFillConstant.SET_UPDATE_USER, currentId);
        } else if (operationType == OperationType.UPDATE) {
            // 更新：只填充更新时间
            setFieldValue(entity, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.now());
            setFieldValue(entity, AutoFillConstant.SET_UPDATE_USER, currentId);

        }


    }


    private void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // 突破private限制
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 如果字段不存在，忽略（可能子类没有该字段）
            log.warn("字段 {} 不存在或设置失败", fieldName);
        }
    }

}
