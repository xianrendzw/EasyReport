package com.easytoolsoft.easyreport.engine.dbpool;

/**
 * 数据源连接池工厂
 */
public class DataSourcePoolFactory {
    public static DataSourcePoolWrapper create(String className) {
        try {
            return (DataSourcePoolWrapper) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException("DataSourcePoolFactory Load Class Error", e);
        }
    }
}
