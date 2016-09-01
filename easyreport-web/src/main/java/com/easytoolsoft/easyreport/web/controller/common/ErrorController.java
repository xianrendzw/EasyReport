package com.easytoolsoft.easyreport.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @GetMapping(value = {"/404"})
    public String error404() {
        return "/error/404";
    }

    @GetMapping(value = "/405")
    public String error405() {
        return "/error/405";
    }

    @GetMapping(value = "/500")
    public String error500() {
        return "/error/500";
    }
}
