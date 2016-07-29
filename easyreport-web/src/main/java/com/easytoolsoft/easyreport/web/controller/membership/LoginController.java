package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户登录页控制器
 */
@Controller
@RequestMapping(value = "/membership")
public class LoginController extends AbstractController {

    @RequestMapping(value = {"/login"})
    public String login() {
        return "membership/login";
    }
}