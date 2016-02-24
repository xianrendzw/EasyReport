package com.easytoolsoft.easyreport.engine.data;

/**
 * 报表数据源类
 */
public class ReportDataSource {
    private String jdbcUrl;
    private String user;
    private String password;

    public ReportDataSource() {
    }

    public ReportDataSource(String url, String user, String password) {
        this.jdbcUrl = url;
        this.user = user;
        this.password = password;
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
     * 设置数据源连接字符串(JDBC)
     *
     * @param jdbcUrl
     */
    public void setJdbcUrl(final String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
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
     * 设置数据源登录用户名
     *
     * @param user
     */
    public void setUser(final String user) {
        this.user = user;
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
     * 设置数据源登录密码
     *
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
