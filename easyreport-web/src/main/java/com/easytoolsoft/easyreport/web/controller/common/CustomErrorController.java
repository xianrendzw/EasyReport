package com.easytoolsoft.easyreport.web.controller.common;

import com.easytoolsoft.easyreport.support.web.CommonErrorController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Slf4j
@Controller
@RequestMapping("/customError")
public class CustomErrorController extends CommonErrorController {
    public CustomErrorController(final ErrorAttributes errorAttributes,
                                 final ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }
}

