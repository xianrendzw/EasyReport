package com.easytoolsoft.easyreport.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = {"/404"})
    public String error404() {
        return "/error/404";
    }

    @RequestMapping(value = "/405")
    public String error405() {
        return "/error/405";
    }

    @RequestMapping(value = "/500")
    public String error500() {
        return "/error/500";
    }
}
