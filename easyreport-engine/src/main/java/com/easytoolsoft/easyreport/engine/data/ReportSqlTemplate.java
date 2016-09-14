package com.easytoolsoft.easyreport.engine.data;

import com.easytoolsoft.easyreport.engine.util.VelocityUtils;

import java.util.Map;

public class ReportSqlTemplate {

    private String sqlTemplate;
    private Map<String, Object> parameters;

    public ReportSqlTemplate(String sqlTemplate, Map<String, Object> parameters) {
        this.sqlTemplate = sqlTemplate;
        this.parameters = parameters;
    }

    public String execute() {
        return VelocityUtils.parse(this.sqlTemplate, this.parameters);
    }
}
