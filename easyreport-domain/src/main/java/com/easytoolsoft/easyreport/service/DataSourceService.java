package com.easytoolsoft.easyreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytoolsoft.easyreport.dao.DataSourceDao;
import com.easytoolsoft.easyreport.data.jdbc.BaseService;
import com.easytoolsoft.easyreport.po.DataSourcePo;

@Service
public class DataSourceService extends BaseService<DataSourceDao, DataSourcePo> {
	@Autowired
	public DataSourceService(DataSourceDao dao) {
		super(dao);
	}
}
