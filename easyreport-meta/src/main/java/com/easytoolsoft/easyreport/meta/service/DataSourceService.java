package com.easytoolsoft.easyreport.meta.service;

import com.easytoolsoft.easyreport.meta.domain.DataSource;
import com.easytoolsoft.easyreport.meta.domain.example.DataSourceExample;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 报表数据源服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface DataSourceService extends CrudService<DataSource, DataSourceExample, Integer> {
    /**
     * 测试当前数据库连接
     *
     * @param driverClass
     * @param url
     * @param user
     * @param password
     */
    boolean testConnection(String driverClass, String url, String user, String password);
}
