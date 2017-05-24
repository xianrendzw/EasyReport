package com.easytoolsoft.easyreport.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统操作日志注解
 *
 * @author Tom Deng
 * @date 2017-03-25
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {
    /**
     * 操作日志名称
     *
     * @return
     */
    String name() default "";

    /**
     * 操作日常说明
     *
     * @return
     */
    String desc() default "";
}
