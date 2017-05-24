package com.easytoolsoft.easyreport.web.spring.aop;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.easytoolsoft.easyreport.membership.domain.Event;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.service.EventService;
import com.easytoolsoft.easyreport.support.aop.OpLogAspect;
import com.easytoolsoft.easyreport.support.consts.UserAuthConsts;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
@Slf4j
@Aspect
@Component
public class CustomOpLogAspect extends OpLogAspect {
    @Resource
    private EventService eventService;

    @Override
    protected void logEvent(final EventParameter eventParameter) {
        final HttpServletRequest req =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        final User user = (User)req.getAttribute(UserAuthConsts.CURRENT_USER);
        if (user != null) {
            final Event event = Event.builder()
                .source(eventParameter.getSource())
                .account(user.getAccount())
                .userId(user.getId())
                .message(eventParameter.toString())
                .level(eventParameter.getLevel())
                .url(req.getRequestURL().toString())
                .gmtCreated(new Date())
                .build();
            this.eventService.add(event);
        }
    }
}

