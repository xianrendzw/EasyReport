package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ColumnType;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.remark.IRemarks;
import com.easytoolsoft.easyreport.engine.remark.MysqlRemarks;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySqlQueryer extends AbstractQueryer implements Queryer {

    public MySqlQueryer(ReportDataSource dataSource, ReportParameter parameter) {
        super(dataSource, parameter);
    }

    @Override
    protected Connection getJdbcConnection() {
        try {
            return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(), this.dataSource.getPassword());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected String preprocessSqlText(String sqlText) {
        sqlText = StringUtils.stripEnd(sqlText.trim(), ";");
        Pattern pattern = Pattern.compile("limit.*?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sqlText);
        if (matcher.find()) {
            sqlText = matcher.replaceFirst("");
        }
        return sqlText + " limit 1";
    }

    protected void buildRemark(Connection conn, List<ReportMetaDataColumn> columns, String sqlText) {
        try {
            IRemarks remarks = new MysqlRemarks();
            Map<String, String> columnRemarks = remarks.getColumnRemarksBySql(conn, sqlText);
            for (int i = 0; i < columns.size(); i++) {
                ReportMetaDataColumn column = columns.get(i);
                column.setText(columnRemarks.get(column.getName()));
                //设置默认字段类型，减少配置工作
                if (i == 1) {
                    column.setType(ColumnType.LAYOUT.getValue());
                } else if (i == 2) {
                    column.setType(ColumnType.DIMENSION.getValue());
                } else {
                    column.setType(ColumnType.STATISTICAL.getValue());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public List<ReportMetaDataColumn> parseMetaDataColumns(String sqlText) {
        List<ReportMetaDataColumn> result = super.parseMetaDataColumns(sqlText);
        if (result.size() == 0) {
            return result;
        }

        return result;
    }

}
