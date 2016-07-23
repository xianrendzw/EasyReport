package com.easytoolsoft.easyreport.metadata.service;

import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.metadata.po.DataSource;

import java.sql.SQLException;

/**
 * 报表数据源服务类
 *
 * @author Tom Deng
 */
public interface IDataSourceService extends ICrudService<DataSource> {
    /**
     * 测试当前数据库连接
     *
     * @param url
     * @param user
     * @param password
     */
    boolean testConnection(String url, String user, String password) throws SQLException;
}
