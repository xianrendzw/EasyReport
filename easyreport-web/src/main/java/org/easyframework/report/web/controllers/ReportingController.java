package org.easyframework.report.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Reporting控制器
 */
@Controller
@RequestMapping(value = "/reporting")
public class ReportingController extends AbstractController {

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {
		return "/reporting/index";
	}
}