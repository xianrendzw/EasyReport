package com.easytoolsoft.easyreport.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 动态生成的系统资源文件(js,css等)控制器
 */
@Controller
@RequestMapping(value = "/dynamic")
public class ResourceController {
    @GetMapping(value = {"/js/init.js"})
    public String initJs(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("lng", request.getLocale());
        response.setContentType("application/x-javascript;charset=utf-8");
        response.setHeader("Cache-control", "no-cache");
        return "resource/init-js";
    }
}
