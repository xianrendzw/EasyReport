package com.easytoolsoft.easyreport.web.controller.common;

import com.easytoolsoft.easyreport.membership.common.Constants;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.data.sys.po.Event;
import com.easytoolsoft.easyreport.sys.service.IEventService;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 *
 */
@Slf4j
public abstract class AbstractController {
    @Resource
    private IEventService eventService;

    protected AbstractController() {
    }

    protected String getSource() {
        return this.getClass().getName();
    }

    protected void logSuccessResult(JsonResult result, String msg, HttpServletRequest req) {
        result.setSuccess(true);
        result.setMsg(StringUtils.isBlank(msg) ? "操作成功！" : msg);
        this.logResult(result, req);
    }

    protected void logFailureResult(JsonResult result, String msg, HttpServletRequest req) {
        result.setSuccess(false);
        result.setMsg(StringUtils.isBlank(msg) ? "操作失败！" : msg);
        this.logResult(result, req);
    }

    protected void logExceptionResult(JsonResult result, Exception ex, String msg, HttpServletRequest req) {
        result.setSuccess(false);
        result.setMsg(log.isDebugEnabled() ?
                String.format("系统异常, 原因:%s", ex.getMessage()) :
                (StringUtils.isBlank(msg) ? "操作失败！" : msg));
        this.logException(ex, req);
        this.logEvent(ex, req);
    }

    protected void logResult(JsonResult result, HttpServletRequest req) {
        this.logEvent(result.getMsg(), req);
    }

    protected void logEvent(Exception ex, HttpServletRequest req) {
        try (StringWriter out = new StringWriter()) {
            PrintWriter printWriter = new PrintWriter(out);
            ex.printStackTrace(printWriter);
            this.logEvent(out.toString(), "ERROR", req);
            printWriter.close();
        } catch (Exception e) {
            this.logException(e, req);
        }
    }

    protected void logEvent(String message, HttpServletRequest req) {
        this.logEvent(message, "INFO", req);
    }

    protected void logEvent(String message, String level, HttpServletRequest req) {
        try {
            User user = (User) req.getAttribute(Constants.CURRENT_USER);
            if (user != null) {
                Event event = Event.builder()
                        .source(this.getSource())
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

    protected void logException(Exception ex, HttpServletRequest req) {
        this.logException("系统异常", ex, req);
    }

    protected void logException(String msg, Exception ex, HttpServletRequest req) {
        log.error(msg, ex);
    }

    protected String getCurrentAccount() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return principal == null ? "anonymous" : (String) principal;
    }
}
