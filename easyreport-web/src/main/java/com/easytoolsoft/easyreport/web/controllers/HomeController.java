package com.easytoolsoft.easyreport.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Reporting控制器
 */
@Controller
@RequestMapping(value = "/")
public class HomeController extends AbstractController {

    @RequestMapping(value = {"/login"})
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "home";
    }
}