package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

/**
 * MS SQLServer 数据库查询器类。
 * 在使用该查询器时,请先参考:https://msdn.microsoft.com/library/mt484311.aspx
 * 获取sqlserver jdbc driver,然后把相关jdbc driver的jar包加入该系统的类路径下(如WEB-INF/lib)
 *
 * @author tomdeng
 */
public class SqlServerQueryer extends AbstractQueryer implements Queryer {
    public SqlServerQueryer(final ReportDataSource dataSource, final ReportParameter parameter) {
        super(dataSource, parameter);
    }
}
