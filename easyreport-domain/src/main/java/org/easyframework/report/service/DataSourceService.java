package org.easyframework.report.service;

import org.easyframework.report.dao.DataSourceDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.po.DataSourcePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService extends BaseService<DataSourceDao, DataSourcePo> {
	@Autowired
	public DataSourceService(DataSourceDao dao) {
		super(dao);
	}
}
