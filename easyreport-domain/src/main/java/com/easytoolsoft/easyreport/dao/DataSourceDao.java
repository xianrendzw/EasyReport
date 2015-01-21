package com.easytoolsoft.easyreport.dao;

import java.sql.DriverManager;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.easytoolsoft.easyreport.data.PageInfo;
import com.easytoolsoft.easyreport.data.criterion.Restrictions;
import com.easytoolsoft.easyreport.data.jdbc.BaseDao;
import com.easytoolsoft.easyreport.po.DataSourcePo;

/**
 * DataSourceDao提供数据源配置信息表表(datasource)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class DataSourceDao extends BaseDao<DataSourcePo> {

	public DataSourceDao() {
		super(DataSourcePo.EntityName, DataSourcePo.Id);
	}

	public boolean testConnection(String url, String pass, String user) {
		try {
			DriverManager.getConnection(url, user, pass);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<DataSourcePo> queryByPage(String createUser, PageInfo page) {
		String condition = Restrictions.equal(DataSourcePo.CreateUser, "?").toString();
		Object[] args = new Object[] { createUser };
		return this.query(condition, page, args);
	}
}