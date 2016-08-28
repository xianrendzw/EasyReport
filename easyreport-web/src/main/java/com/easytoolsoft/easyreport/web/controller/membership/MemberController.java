package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.membership.service.MembershipFacade;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户登录页控制器
 */
@Slf4j
@Controller
@RequestMapping(value = "/membership")
public class MemberController {
    @Resource
    private MembershipFacade membershipFacade;

    @RequestMapping(value = {"/login"})
    public String login() {
        return "membership/login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "/membership/login";
    }

    @RequestMapping(value = "/profile")
    public String profile(@CurrentUser User loginUser, Model model) {
        model.addAttribute("roleNames", membershipFacade.getRoleNames(loginUser.getRoles()));
        return "membership/profile";
    }

    @ResponseBody
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public JsonResult authenticate(String account, String password, boolean rememberMe) {
        JsonResult result = new JsonResult(false, "用户名/密码错误!");
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            token.setRememberMe(rememberMe);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            result.setSuccess(true);
            result.setMsg("登录成功!");
        } catch (IncorrectCredentialsException | UnknownAccountException ex) {
            result.setMsg("用户名/密码错误!");
        } catch (Exception ex) {
            if (ex.getClass().getSimpleName().equals("LockedAccountException")) {
                result.setMsg("您的账号已经被锁定!");
            } else if (ex.getClass().getSimpleName().equals("ExcessiveAttemptsException")) {
                result.setMsg("您重试密码超过10次,账号已被锁定!");
            }
        }
        return result;
    }
}