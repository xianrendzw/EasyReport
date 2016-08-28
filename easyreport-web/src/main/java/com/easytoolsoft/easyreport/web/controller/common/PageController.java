package com.easytoolsoft.easyreport.web.controller.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 通用的页面控件器
 */
@Controller
public class PageController {
    @RequestMapping(
            value = {"/views/{path1}"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1) {
        return this.routeTo(path1);
    }

    @RequestMapping(
            value = {"/views/{path1}/{path2}"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2) {
        return this.routeTo(path1, path2);
    }

    @RequestMapping(
            value = {"/views/{path1}/{path2}/{path3}"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2,
                     @PathVariable("path3") String path3) {
        return this.routeTo(path1, path2, path3);
    }

    @RequestMapping(
            value = {"/views/{path1}/{path2}/{path3}/{path4}"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2,
                     @PathVariable("path3") String path3,
                     @PathVariable("path4") String path4) {
        return this.routeTo(path1, path2, path3, path4);
    }

    @RequestMapping(
            value = {"/views/{path1}/{path2}/{path3}/{path4}/{path5}"},
            method = {RequestMethod.GET}
    )
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2,
                     @PathVariable("path3") String path3,
                     @PathVariable("path4") String path4,
                     @PathVariable("path5") String path5) {
        return this.routeTo(path1, path2, path3, path4, path5);
    }

    private String routeTo(String... paths) {
        return Arrays.asList(paths).stream()
                .filter(StringUtils::isNotBlank).collect(Collectors.joining("/"));
    }
}
