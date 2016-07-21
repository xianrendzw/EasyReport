package com.easytoolsoft.easyreport.metadata.service.impl;

import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.metadata.dao.IDataSourceDao;
import com.easytoolsoft.easyreport.metadata.po.DataSource;
import com.easytoolsoft.easyreport.metadata.service.IDataSourceService;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;

@Service("EzrptMetaSourceService")
public class DataSourceService extends AbstractCrudService<IDataSourceDao, DataSource> implements IDataSourceService {

    public boolean testConnection(String url, String user, String password) {
        try {
            DriverManager.getConnection(url, user, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
