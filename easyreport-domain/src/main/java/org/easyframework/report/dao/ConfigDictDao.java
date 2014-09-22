package org.easyframework.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyframework.report.data.criterion.Restrictions;
import org.easyframework.report.data.jdbc.BaseDao;
import org.easyframework.report.entity.ConfigDict;
import org.springframework.stereotype.Repository;

/**
 * ConfigDictDao提供配置字典表表(config_dict)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ConfigDictDao extends BaseDao<ConfigDict> {

	public ConfigDictDao() {
		super(ConfigDict.EntityName, ConfigDict.Id);
	}

	public ConfigDict queryByDictKey(String dictKey) {
		String condition = Restrictions.equal(ConfigDict.Key, "?").toString();
		Object[] args = new Object[] { dictKey };
		return this.queryOne(condition, args);
	}

	public List<ConfigDict> queryByPid(int pid) {
		String condition = Restrictions.equal(ConfigDict.Pid, "?").toString();
		Object[] args = new Object[] { pid };
		return this.query(condition, args);
	}

	public List<ConfigDict> queryByParentDictKey(String parentDictKey) {
		String columnValue = String.format("select %s from %s where `%s` = ?",
				ConfigDict.Id, this.tableName, ConfigDict.Key);
		String condition = Restrictions.in(ConfigDict.Pid, columnValue).toString();
		Object[] args = new Object[] { parentDictKey };
		return this.query(condition, args);
	}

	public List<ConfigDict> queryInByParentDictKeys(String parentDictKeys) {
		String columnValue = String.format("select %s from %s where `%s` in(%s)",
				ConfigDict.Id, this.tableName, ConfigDict.Key, parentDictKeys);
		String condition = Restrictions.in(ConfigDict.Pid, columnValue).toString();
		return this.query(condition);
	}

	public Map<String, ConfigDict> queryForMap(int pid) {
		return this.listToMap(this.queryByPid(pid));
	}

	public Map<String, ConfigDict> queryForMap(String parentDictKey) {
		return this.listToMap(this.queryByParentDictKey(parentDictKey));
	}

	private Map<String, ConfigDict> listToMap(List<ConfigDict> configDicts) {
		if (configDicts == null || configDicts.size() == 0) {
			return new HashMap<String, ConfigDict>(0);
		}

		Map<String, ConfigDict> configDictMap = new HashMap<>(configDicts.size());
		for (ConfigDict po : configDicts) {
			String key = po.getKey().trim().toLowerCase();
			if (!configDictMap.containsKey(key)) {
				configDictMap.put(key, po);
			}
		}

		return configDictMap;
	}
}