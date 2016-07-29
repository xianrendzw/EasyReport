package com.easytoolsoft.easyreport.web.controller;

import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home页控制器
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends AbstractController {
    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "home";
    }
}