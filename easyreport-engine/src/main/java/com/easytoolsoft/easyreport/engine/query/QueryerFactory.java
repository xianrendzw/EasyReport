package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

import java.lang.reflect.Constructor;

/**
 * 报表查询器工厂方法类
 */
public class QueryerFactory {
    public static Queryer create(ReportDataSource dataSource) {
        return create(dataSource, null);
    }

    public static Queryer create(ReportDataSource dataSource, ReportParameter parameter) {
        if (dataSource != null) {
            try {
                Class<?> clazz = Class.forName(dataSource.getQueryerClass());
                Constructor<?> constructor = clazz.getConstructor(ReportDataSource.class, ReportParameter.class);
                return (Queryer) constructor.newInstance(dataSource, parameter);
            } catch (Exception ex) {
                throw new RuntimeException("create report engine queryer error", ex);
            }
        }
        return null;
    }
}
