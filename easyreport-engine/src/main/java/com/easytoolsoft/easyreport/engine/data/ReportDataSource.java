package com.easytoolsoft.easyreport.engine.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 报表数据源类
 *
 * @author tomdeng
 */
public class ReportDataSource {
    private final String uid;
    private final String queryerClass;
    private final String dbPoolClass;
    private final String driverClass;
    private final String jdbcUrl;
    private final String user;
    private final String password;
    private final Map<String, Object> options;

    public ReportDataSource(final String uid, final String driverClass, final String jdbcUrl, final String user,
                            final String password,
                            final String queryerClass, final String dbPoolClass) {
        this(uid, driverClass, jdbcUrl, user, password, queryerClass, dbPoolClass, new HashMap<>(3));
    }

    public ReportDataSource(final String uid, final String driverClass, final String jdbcUrl, final String user,
                            final String password,
                            final String queryerClass, final String dbPoolClass,
                            final Map<String, Object> options) {
        this.uid = uid;
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.queryerClass = queryerClass;
        this.dbPoolClass = dbPoolClass;
        this.options = options;
    }

    /**
     * 获取数据源唯一标识
     *
     * @return 数据源唯一标识
     */
    public String getUid() {
        return this.uid;
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
     * 获取报表引擎查询器类名(如:com.easytoolsoft.easyreport.engine.query.MySqlQueryer)
     *
     * @return 具体Queryer类完全名称
     * @see com.easytoolsoft.easyreport.engine.query.Queryer
     */
    public String getQueryerClass() {
        return this.queryerClass;
    }

    /**
     * 获取报表引擎查询器使用的数据源连接池类名(如:com.easytoolsoft.easyreport.engine.dbpool.C3p0DataSourcePool)
     *
     * @return 具体DataSourcePoolWrapper类完全名称
     * @see com.easytoolsoft.easyreport.engine.dbpool.DataSourcePoolWrapper
     */
    public String getDbPoolClass() {
        return this.dbPoolClass;
    }

    /**
     * 获取数据源配置选项,如果没有配置选项则设置为默认选项
     *
     * @return 数据源配置选项
     */
    public Map<String, Object> getOptions() {
        return this.options;
    }
}
