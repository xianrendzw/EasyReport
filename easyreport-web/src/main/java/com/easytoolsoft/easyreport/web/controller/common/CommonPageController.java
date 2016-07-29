package com.easytoolsoft.easyreport.web.controller.common;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ObjectArrays;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 通用的页面控件器
 */
@Controller
public class CommonPageController {
    private static final Joiner joiner = Joiner.on('/');

    public CommonPageController() {
    }

    @RequestMapping(
            value = {"/pages/{page}.html"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("page") String page) {
        return this.routeTo(page);
    }

    @RequestMapping(
            value = {"/pages/{path1}/{page}.html"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("page") String page) {
        return this.routeTo(page, new String[]{path1});
    }

    @RequestMapping(
            value = {"/pages/{path1}/{path2}/{page}.html"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2,
                     @PathVariable("page") String page) {
        return this.routeTo(page, new String[]{path1, path2});
    }

    @RequestMapping(
            value = {"/pages/{path1}/{path2}/{path3}/{page}.html"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2,
                     @PathVariable("path3") String path3,
                     @PathVariable("page") String page) {
        return this.routeTo(page, new String[]{path1, path2, path3});
    }

    @RequestMapping(
            value = {"/pages/{path1}/{path2}/{path3}/{path4}/{page}.html"},
            method = {RequestMethod.GET})
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2,
                     @PathVariable("path3") String path3,
                     @PathVariable("path4") String path4,
                     @PathVariable("page") String page) {
        return this.routeTo(page, new String[]{path1, path2, path3, path4});
    }

    @RequestMapping(
            value = {"/pages/{path1}/{path2}/{path3}/{path4}/{path5}/{page}.html"},
            method = {RequestMethod.GET}
    )
    public String to(@PathVariable("path1") String path1,
                     @PathVariable("path2") String path2,
                     @PathVariable("path3") String path3,
                     @PathVariable("path4") String path4,
                     @PathVariable("path5") String path5,
                     @PathVariable("page") String page) {
        return this.routeTo(page, new String[]{path1, path2, path3, path4, path5});
    }

    private String routeTo(String page, String... paths) {
        return joiner.join(FluentIterable.of(ObjectArrays.concat(paths, page))
                .filter(StringUtils::isNotBlank));
    }
}
