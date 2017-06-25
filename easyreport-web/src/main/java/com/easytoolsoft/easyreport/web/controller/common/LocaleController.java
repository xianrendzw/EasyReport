package com.easytoolsoft.easyreport.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easytoolsoft.easyreport.support.i18n.LocaleUtils;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhiwei.deng
 * @date 2017-05-26
 **/
@RestController
@RequestMapping("/rest/locale")
public class LocaleController {

    @GetMapping("/{lang}")
    public ResponseResult setLocale(@PathVariable final String lang, final HttpServletRequest request,
                                    final HttpServletResponse response) {
        LocaleUtils.setLocale(lang, request, response);
        return ResponseResult.success(LocaleContextHolder.getLocale());
    }
}
