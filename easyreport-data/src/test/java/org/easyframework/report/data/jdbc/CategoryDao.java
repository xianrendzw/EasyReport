package org.easyframework.report.data.jdbc;

import org.easyframework.report.data.jdbc.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao extends BaseDao<CategoryPo> {
	public CategoryDao() {
		super(CategoryPo.TableName, CategoryPo.Id);
	}
}
