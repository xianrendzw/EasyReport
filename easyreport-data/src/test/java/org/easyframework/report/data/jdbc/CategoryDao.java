package org.easyframework.report.data.jdbc;

import org.springframework.stereotype.Repository;

import com.easyreport.data.jdbc.BaseDao;

@Repository
public class CategoryDao extends BaseDao<CategoryPo> {
	public CategoryDao() {
		super(CategoryPo.TableName, CategoryPo.Id);
	}
}
