package com.easytoolsoft.easyreport.engine.data;

import java.util.Map;

import com.easytoolsoft.easyreport.engine.util.VelocityUtils;

/**
 * @author tomdeng
 */
public class ReportSqlTemplate {

    private final String sqlTemplate;
    private final Map<String, Object> parameters;

    public ReportSqlTemplate(final String sqlTemplate, final Map<String, Object> parameters) {
        this.sqlTemplate = sqlTemplate;
        this.parameters = parameters;
    }

    public String execute() {
        return VelocityUtils.parse(this.sqlTemplate, this.parameters);
    }
}
