package com.easytoolsoft.easyreport.support.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.easytoolsoft.easyreport.support.annotation.OpLog;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
@Slf4j
public class OpLogAspect {
    @Pointcut("@annotation(com.easytoolsoft.easyreport.support.annotation.OpLog)")
    public void pointcut() {
    }

    @After("pointcut()")
    public void doAfter(final JoinPoint joinPoint) {
        try {
            this.logEvent(joinPoint, "INFO", "");
        } catch (final Exception e) {
            log.error("异常信息:{}", e);
        }
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void doAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        try {
            this.logEvent(joinPoint, "ERROR", ExceptionUtils.getStackTrace(e));
        } catch (final Exception ex) {
            log.error("异常信息:{}", ex.getMessage());
        }
    }

    protected void logEvent(final JoinPoint joinPoint, final String level, final String message) {
        try {
            final EventParameter eventParameter = this.getEventParameter(joinPoint, level, message);
            this.logEvent(eventParameter);
        } catch (final Exception e) {
            log.error("记录系统事件出错", e);
        }
    }

    protected void logEvent(final EventParameter eventParameter) {
        final HttpServletRequest req =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        eventParameter.setUrl(req.getRequestURL().toString());
        log.info("OpLog:{}", eventParameter.toString());
    }

    protected EventParameter getEventParameter(final JoinPoint joinPoint, final String level, final String message)
        throws Exception {
        final EventParameter eventParameter = new EventParameter();
        final String targetName = joinPoint.getTarget().getClass().getName();
        final String methodName = joinPoint.getSignature().getName();
        final Object[] arguments = joinPoint.getArgs();
        final Class targetClass = Class.forName(targetName);
        final Method[] methods = targetClass.getMethods();

        eventParameter.setSource(targetName + ":" + methodName);
        eventParameter.setLevel(level);
        eventParameter.setMessage(message);
        for (final Method method : methods) {
            if (method.getName().equals(methodName)) {
                final Class[] methodParameterTypes = method.getParameterTypes();
                if (methodParameterTypes.length == arguments.length) {
                    eventParameter.setName(method.getAnnotation(OpLog.class).name());
                    eventParameter.setDesc(method.getAnnotation(OpLog.class).desc());
                    eventParameter.setArguments(StringUtils.join(arguments, ","));
                    break;
                }
            }
        }
        return eventParameter;
    }

    /**
     * 事件参数类
     */
    @Data
    public static class EventParameter {
        /**
         * 事件来源
         */
        private String source;
        /**
         * 事件级别
         */
        private String level;
        /**
         * 事件名称
         */
        private String name;
        /**
         * 事件说明
         */
        private String desc;
        /**
         * 事件调用方法参数
         */
        private String arguments;
        /**
         * 事件信息
         */
        private String message;
        /**
         * 事件请求url
         */
        private String url;
    }
}

