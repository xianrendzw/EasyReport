package com.easyreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyreport.dao.DataSourceDao;
import com.easyreport.data.jdbc.BaseService;
import com.easyreport.po.DataSourcePo;

@Service
public class DataSourceService extends BaseService<DataSourceDao, DataSourcePo> {
	@Autowired
	public DataSourceService(DataSourceDao dao) {
		super(dao);
	}
}
