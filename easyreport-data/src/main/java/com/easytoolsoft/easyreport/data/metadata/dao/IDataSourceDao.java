package com.easytoolsoft.easyreport.data.metadata.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.metadata.example.DataSourceExample;
import com.easytoolsoft.easyreport.data.metadata.po.DataSource;
import org.springframework.stereotype.Repository;

/**
 * 报表数据源(ezrpt_meta_datasource表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMetaIDataSourceDao")
public interface IDataSourceDao extends ICrudDao<DataSource, DataSourceExample> {
}
