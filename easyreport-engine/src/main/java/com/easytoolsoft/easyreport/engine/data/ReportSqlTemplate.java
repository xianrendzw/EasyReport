package com.easytoolsoft.easyreport.engine.data;

import java.util.Map;

import com.easytoolsoft.easyreport.engine.util.VelocityUtils;

public class ReportSqlTemplate {

	private String sqlTemplate;
	private Map<String, Object> parameters;

	public ReportSqlTemplate(String sqlTemplate, Map<String, Object> parameters) {
		this.sqlTemplate = sqlTemplate;
		this.parameters = parameters;
	}

	public String execute() {
		return VelocityUtils.prase(this.sqlTemplate, this.parameters);
	}
}
