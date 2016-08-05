package com.easytoolsoft.easyreport.web.spring.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
