package com.easytoolsoft.easyreport.web.spring.aop;

import com.alibaba.fastjson.JSON;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.data.sys.po.Event;
import com.easytoolsoft.easyreport.membership.common.Constants;
import com.easytoolsoft.easyreport.sys.service.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class OpLogAspect {
    @Resource
    private IEventService eventService;

    @Pointcut("@annotation(com.easytoolsoft.easyreport.web.spring.aop.OpLog)")
    public void pointcut() {
    }

    @After("pointcut()")
    public void doAfter(JoinPoint joinPoint) {
        try {
            this.logEvent(joinPoint, "INFO", "");
        } catch (Exception e) {
            log.error("异常信息:{}", e);
        }
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            this.logEvent(joinPoint, "ERROR", this.getExceptionStack(e));
        } catch (Exception ex) {
            log.error("异常信息:{}", ex.getMessage());
        }
    }

    private void logEvent(JoinPoint joinPoint, String level, String message) {
        try {
            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            User user = (User) req.getAttribute(Constants.CURRENT_USER);
            if (user != null) {
                Map<String, String> methodInfo = this.getMethodInfo(joinPoint);
                String source = MapUtils.getString(methodInfo, "source", "") + ";params:" + MapUtils.getString(methodInfo, "params", "");
                message = MapUtils.getString(methodInfo, "name", "") + ";" + MapUtils.getString(methodInfo, "desc", "") + "detail:" + message;
                Event event = Event.builder().source(source)
                        .account(user.getAccount())
                        .userId(user.getId())
                        .message(message)
                        .level(level)
                        .url(req.getRequestURL().toString())
                        .gmtCreated(new Date())
                        .build();
                this.eventService.add(event);
            }
        } catch (Exception e) {
            log.error("记录系统事件出错", e);
        }
    }

    private Map<String, String> getMethodInfo(JoinPoint joinPoint) throws Exception {
        Map<String, String> methodInfoMap = new HashMap<>(3);
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        methodInfoMap.put("source", targetName + ":" + methodName);
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] methodParameterTypes = method.getParameterTypes();
                if (methodParameterTypes.length == arguments.length) {
                    methodInfoMap.put("name", method.getAnnotation(OpLog.class).name());
                    methodInfoMap.put("desc", method.getAnnotation(OpLog.class).desc());
                    methodInfoMap.put("params", this.getMethodParams(arguments));
                    break;
                }
            }
        }
        return methodInfoMap;
    }

    private String getMethodParams(Object[] arguments) {
        if (ArrayUtils.isNotEmpty(arguments)) {
            return JSON.toJSONString(arguments);
        }
        return StringUtils.EMPTY;
    }

    private String getExceptionStack(Throwable ex) {
        String stackInfo = "";
        try (StringWriter out = new StringWriter()) {
            PrintWriter printWriter = new PrintWriter(out);
            ex.printStackTrace(printWriter);
            stackInfo = out.toString();
            printWriter.close();
        } catch (Exception e) {
            log.error("获取异常堆栈信息出错", e);
        }
        return stackInfo;
    }
}

