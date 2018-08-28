package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class H2Queryer extends AbstractQueryer implements Queryer {
    public H2Queryer(final ReportDataSource dataSource, final ReportParameter parameter) {
        super(dataSource, parameter);
    }

    @Override
    protected String preprocessSqlText(String sqlText) {
        sqlText = sqlText.replace('"', '\'');
        return sqlText;
    }
}
