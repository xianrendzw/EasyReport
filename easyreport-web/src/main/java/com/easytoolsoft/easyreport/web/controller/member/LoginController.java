package com.easytoolsoft.easyreport.web.controller.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easytoolsoft.easyreport.membership.service.EventService;
import com.easytoolsoft.easyreport.support.i18n.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户登录页控制器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Slf4j
@Controller
@RequestMapping(value = "/member")
public class LoginController {
    @Resource
    private EventService eventService;

    @GetMapping("/login/{lang}")
    public ModelAndView language(@PathVariable final String lang, final HttpServletRequest request,
                                 final HttpServletResponse response) {
        LocaleUtils.setLocale(lang, request, response);
        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = {"/login"})
    public String login(final HttpServletRequest request, final HttpServletResponse response) {
        LocaleUtils.setInitLocale(request, response);
        log.info(LocaleUtils.getMessage("view.member.login.account"));
        return "member/login";
    }
}