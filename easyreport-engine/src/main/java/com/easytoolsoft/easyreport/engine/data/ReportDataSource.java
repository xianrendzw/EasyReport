package com.easytoolsoft.easyreport.engine.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 报表数据源类
 */
public class ReportDataSource {
    private final String uid;
    private final String driverClass;
    private final String jdbcUrl;
    private final String user;
    private final String password;
    private final Map<String, Object> options;

    public ReportDataSource(String uid, String driverClass, String jdbcUrl, String user, String password) {
        this(uid, driverClass, jdbcUrl, user, password, new HashMap<>(3));
    }

    public ReportDataSource(String uid, String driverClass, String jdbcUrl, String user, String password,
                            Map<String, Object> options) {
        this.uid = uid;
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.options = options;
    }

    /**
     * 获取数据源唯一标识
     *
     * @return 数据源唯一标识
     */
    public String getUid() {
        return uid;
    }

    /**
     * 获取数据源驱动类
     *
     * @return 数据源驱动类
     */
    public String getDriverClass() {
        return this.driverClass;
    }


    /**
     * 获取数据源连接字符串(JDBC)
     *
     * @return 数据源连接字符串(JDBC)
     */
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    /**
     * 获取数据源登录用户名
     *
     * @return 数据源登录用户名
     */
    public String getUser() {
        return this.user;
    }

    /**
     * 获取数据源登录密码
     *
     * @return 数据源登录密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 获取数据源配置选项,如果没有配置选项则设置为默认选项
     *
     * @return 数据源配置选项
     */
    public Map<String, Object> getOptions() {
        return options;
    }
}
