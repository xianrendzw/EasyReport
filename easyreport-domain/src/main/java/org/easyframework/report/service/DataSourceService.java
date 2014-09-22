package org.easyframework.report.service;

import org.easyframework.report.dao.DataSourceDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.entity.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService extends BaseService<DataSourceDao, DataSource> {
	@Autowired
	public DataSourceService(DataSourceDao dao) {
		super(dao);
	}
}
