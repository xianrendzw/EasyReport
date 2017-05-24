package com.easytoolsoft.easyreport.web.controller.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easytoolsoft.easyreport.membership.service.EventService;
import com.easytoolsoft.easyreport.support.i18n.LocaleUtils;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String login(final Model model, final HttpServletRequest request, final HttpServletResponse response) {
        LocaleUtils.setInitLocale(request, response);
        log.info(LocaleUtils.getMessage("view.member.login.account"));
        return "member/login";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "/member/login";
    }

    @ResponseBody
    @PostMapping(value = "/authenticate")
    public ResponseResult authenticate(final String account, final String password, final boolean rememberMe,
                                       final HttpServletRequest req) {
        ResponseResult result = null;
        try {
            final UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            token.setRememberMe(rememberMe);
            final Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            result = ResponseResult.success(true);
        } catch (IncorrectCredentialsException | UnknownAccountException ex) {
            result = ResponseResult.failure(10000,
                LocaleUtils.getMessage("ctrl.member.login.IncorrectCredentialsException"),
                ex.getMessage());
        } catch (final Exception ex) {
            if ("LockedAccountException".equals(ex.getClass().getSimpleName())) {
                result = ResponseResult.failure(10001,
                    LocaleUtils.getMessage("ctrl.member.login.LockedAccountException"),
                    ex.getMessage());
            } else if ("ExcessiveAttemptsException".equals(ex.getClass().getSimpleName())) {
                result = ResponseResult.failure(10002,
                    LocaleUtils.getMessage("ctrl.member.login.ExcessiveAttemptsException"),
                    ex.getMessage());
            }
        }
        this.eventService.add("authenticate", account, "用户登录", "INFO", req.getRequestURL().toString());
        return result;
    }
}