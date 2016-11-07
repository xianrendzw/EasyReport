package com.easytoolsoft.easyreport.domain.metadata.service;

import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.metadata.example.DataSourceExample;
import com.easytoolsoft.easyreport.data.metadata.po.DataSource;

/**
 * 报表数据源服务类
 *
 * @author Tom Deng
 */
public interface IDataSourceService extends ICrudService<DataSource, DataSourceExample> {
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
