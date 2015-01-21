package com.easytoolsoft.easyreport.data.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.easytoolsoft.easyreport.data.BaseTest;
import com.easytoolsoft.easyreport.data.criterion.Restrictions;
import com.easytoolsoft.easyreport.data.util.SpringContextUtils;

public class BaseDaoTest extends BaseTest {

	protected CategoryDao categoryDao;

	@Before
	public void init() {
		this.categoryDao = SpringContextUtils.getBean(CategoryDao.class);
	}

	@Test
	public void testGetTableName() {
		String tableName = this.categoryDao.getTableName();
		Assert.assertThat(tableName, IsEqual.equalTo("category"));
	}

	@Test
	public void testSetTableName() {
		this.categoryDao.setTableName("category");
		String tableName = this.categoryDao.getTableName();
		Assert.assertThat(tableName, IsEqual.equalTo("category"));
	}

	@Test
	public void testGetPrimaryKey() {
		String keyName = this.categoryDao.getPrimaryKey();
		Assert.assertThat(keyName, IsEqual.equalTo("id"));
	}

	@Test
	public void testSetPrimaryKey() {
		this.categoryDao.setPrimaryKey("id");
		String keyName = this.categoryDao.getPrimaryKey();
		Assert.assertThat(keyName, IsEqual.equalTo("id"));
	}

	@Test
	public void testInsert() {
		CategoryPo po = getEntity();
		this.categoryDao.insert(po);

		String condition = Restrictions.equal(CategoryPo.Name, "?").toString();
		Object[] args = new Object[] { "category1" };
		po = this.categoryDao.queryOne(condition, args);

		Assert.assertThat(po.getName(), IsEqual.equalTo("category1"));
		Assert.assertThat(po.getComment(), IsEqual.equalTo("desc1"));
		Assert.assertThat(po.getParentName(), IsEqual.equalTo("parent_name1"));
		Assert.assertThat(po.getCreateTime(), IsEqual.equalTo("2014-07-05 00:00:00.0"));
		Assert.assertThat(po.getUpdateTime(), IsEqual.equalTo("2014-07-05 00:00:00.0"));
	}

	@Test
	public void testInsertWithId() {
		CategoryPo po = getEntity();
		int id = this.categoryDao.insertWithId(po);
		po = this.categoryDao.queryByKey(id);

		Assert.assertThat(po.getName(), IsEqual.equalTo("category1"));
		Assert.assertThat(po.getComment(), IsEqual.equalTo("desc1"));
		Assert.assertThat(po.getParentName(), IsEqual.equalTo("parent_name1"));
		Assert.assertThat(po.getCreateTime(), IsEqual.equalTo("2014-07-05 00:00:00.0"));
		Assert.assertThat(po.getUpdateTime(), IsEqual.equalTo("2014-07-05 00:00:00.0"));
	}

	@Test
	public void testBatchInsert() {
		List<CategoryPo> entities = getEntities();
		int effectedRows = this.categoryDao.batchInsert(entities);
		Assert.assertThat(effectedRows, IsEqual.equalTo(10));
	}

	@Test
	public void testDeleteWithConditionArgs() {
		List<CategoryPo> entities = getEntities();
		this.categoryDao.batchInsert(entities);

		String condition = String.format("%s = ?", CategoryPo.Name);
		Object[] args = new Object[] { "category1" };
		int effectedRows = this.categoryDao.delete(condition, args);
		boolean isExist = this.categoryDao.isExist(condition, args);

		Assert.assertTrue(effectedRows > 0);
		Assert.assertTrue(isExist == false);
	}

	@Test
	public void testDeleteAll() {
		List<CategoryPo> entities = getEntities();
		this.categoryDao.batchInsert(entities);
		int count = this.categoryDao.count();
		Assert.assertTrue(count > 0);

		this.categoryDao.deleteAll();
		count = this.categoryDao.count();
		Assert.assertTrue(count == 0);
	}

	@Test
	public void testTruncate() {
		List<CategoryPo> entities = getEntities();
		this.categoryDao.batchInsert(entities);
		int count = this.categoryDao.count();
		Assert.assertTrue(count > 0);

		this.categoryDao.truncate();
		count = this.categoryDao.count();
		Assert.assertTrue(count == 0);
	}

	@Test
	public void testDeleteByIntKey() {

	}

	@Test
	public void testDeleteByStringKey() {
	}

	@Test
	public void testDeleteByIntKeys() {
	}

	@Test
	public void testDeleteByStringKeys() {
	}

	@Test
	public void testDeleteInByKey() {
	}

	@Test
	public void testUpdateWithConditionArgsColumnNames() {
	}

	@Test
	public void testUpdateWithConditionColumnNames() {
	}

	@Test
	public void testUpdateWithIntKeyColumnNames() {
	}

	@Test
	public void testUpdateWithStringKeyColumnNames() {
	}

	@Test
	public void testUpdateInByKey() {
	}

	@Test
	public void testSelectOne() {
	}

	@Test
	public void testSelect() {
	}

	@Test
	public void testSelectWithColumnNames() {
	}

	@Test
	public void testSelectWithSortColumnNames() {
	}

	@Test
	public void testSelectWithConditionColumnNames() {
	}

	@Test
	public void testSelectWithConditionArgsColumnNames() {
	}

	@Test
	public void testSelectWithConditionSortArgsColumnNames() {
	}

	@Test
	public void testSelectWithConditionPageArgsColumnNames() {
	}

	@Test
	public void testSelectByIntKey() {
	}

	@Test
	public void testSelectByStringKey() {
	}

	@Test
	public void testSelectMaxId() {
	}

	@Test
	public void testSelectMaxValue() {
	}

	@Test
	public void testCount() {
	}

	@Test
	public void testCountWithConditionArgs() {
	}

	@Test
	public void testIsExistWithConditionArgs() {
	}

	@Test
	public void testIsExistByIntKey() {
	}

	@Test
	public void testIsExistByIntKeys() {
	}

	@Test
	public void testIsExistByStringKey() {
	}

	@Test
	public void testIsExistByStringKeys() {
	}

	@Test
	public void testIsExistInByKey() {
	}

	private List<CategoryPo> getEntities() {
		List<CategoryPo> entities = new ArrayList<CategoryPo>(10);
		for (int i = 0; i < 10; i++) {
			CategoryPo po = this.getEntity(i);
			entities.add(po);
		}
		return entities;
	}

	private CategoryPo getEntity() {
		return this.getEntity(1);
	}

	private CategoryPo getEntity(int i) {
		CategoryPo po = new CategoryPo();
		po.setName("category" + i);
		po.setComment("desc" + i);
		po.setParentId(0);
		po.setParentName("parent_name" + i);
		po.setCreateTime("2014-07-05");
		po.setUpdateTime("2014-07-05");
		return po;
	}

}
