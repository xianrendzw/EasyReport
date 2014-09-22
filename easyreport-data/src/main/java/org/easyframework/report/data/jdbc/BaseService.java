package org.easyframework.report.data.jdbc;

import java.util.List;

import org.easyframework.report.data.PageInfo;

public abstract class BaseService<TDao extends BaseDao<TEntity>, TEntity> {
	protected TDao dao;

	public BaseService(TDao dao) {
		this.dao = dao;
	}

	/**
	 *
	 * @return
	 */
	public TDao getDao() {
		return this.dao;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public boolean isExist(int id) {
		return this.dao.isExistByKey(id);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public boolean isExist(String id) {
		return this.dao.isExistByKey(id);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	public int addWithId(TEntity entity) {
		return this.dao.insertWithId(entity);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	public boolean add(TEntity entity) {
		return this.dao.insert(entity) > 0;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public boolean remove(int id) {
		return this.dao.deleteByKey(id) > 0;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public boolean remove(String id) {
		return this.dao.delete(id) > 0;
	}

	/**
	 *
	 * @param ids
	 * @return
	 */
	public boolean removeByIds(String ids) {
		return this.dao.deleteInByKey(ids) > 0;
	}

	/**
	 *
	 */
	public void clear() {
		this.dao.truncate();
	}

	/**
	 *
	 * @param entity
	 * @param columnNames
	 * @return
	 */
	public boolean edit(TEntity entity, String... columnNames) {
		return this.dao.update(entity, columnNames) > 0;
	}

	/**
	 *
	 * @param entity
	 * @param id
	 * @param columnNames
	 * @return
	 */
	public boolean edit(TEntity entity, int id, String... columnNames) {
		return this.dao.update(entity, id, columnNames) > 0;
	}

	/**
	 *
	 * @param entity
	 * @param id
	 * @param columnNames
	 * @return
	 */
	public boolean edit(TEntity entity, String id, String... columnNames) {
		return this.dao.update(entity, id, columnNames) > 0;
	}

	/**
	 *
	 * @param entity
	 * @param ids
	 * @param columnNames
	 * @return
	 */
	public boolean editByIds(TEntity entity, String ids, String... columnNames) {
		return this.dao.updateInByKey(entity, ids, columnNames) > 0;
	}

	/**
	 *
	 * @param id
	 * @param columnNames
	 * @return
	 */
	public TEntity getById(String id, String... columnNames) {
		return this.dao.queryByKey(id, columnNames);
	}

	/**
	 *
	 * @param id
	 * @param columnNames
	 * @return
	 */
	public TEntity getById(int id, String... columnNames) {
		return this.dao.queryByKey(id, columnNames);
	}

	/**
	 *
	 * @param columnNames
	 * @return
	 */
	public List<TEntity> getAll(String... columnNames) {
		return this.dao.query(columnNames);
	}

	/**
	 *
	 * @param pageInfo
	 * @param columnNames
	 * @return
	 */
	public List<TEntity> getByPage(PageInfo page, String... columnNames) {
		return this.dao.query("", page, null, columnNames);
	}

	/**
	 *
	 * @return
	 */
	public int getCount() {
		return this.dao.count();
	}

}
