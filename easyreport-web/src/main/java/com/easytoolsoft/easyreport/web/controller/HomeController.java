package com.easytoolsoft.easyreport.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home页控制器
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController {
    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "home";
    }
}