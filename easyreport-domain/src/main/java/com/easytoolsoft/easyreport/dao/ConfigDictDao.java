package com.easytoolsoft.easyreport.dao;

import com.easytoolsoft.easyreport.data.criterion.Restrictions;
import com.easytoolsoft.easyreport.data.jdbc.BaseDao;
import com.easytoolsoft.easyreport.po.ConfigDictPo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ConfigDictDao提供配置字典表表(config_dict)的相关数据访问操作的类。
 *
 * @author Tom Deng
 */
@Repository
public class ConfigDictDao extends BaseDao<ConfigDictPo> {

    public ConfigDictDao() {
        super(ConfigDictPo.EntityName, ConfigDictPo.Id);
    }

    public ConfigDictPo queryByDictKey(String dictKey) {
        String condition = Restrictions.equal(ConfigDictPo.Key, "?").toString();
        Object[] args = new Object[]{dictKey};
        return this.queryOne(condition, args);
    }

    public List<ConfigDictPo> queryByPid(int pid) {
        String condition = Restrictions.equal(ConfigDictPo.Pid, "?").toString();
        Object[] args = new Object[]{pid};
        return this.query(condition, args);
    }

    public List<ConfigDictPo> queryByParentDictKey(String parentDictKey) {
        String columnValue = String.format("select %s from %s where %s = ?",
                ConfigDictPo.Id, this.tableName, ConfigDictPo.Key);
        String condition = Restrictions.in(ConfigDictPo.Pid, columnValue).toString();
        Object[] args = new Object[]{parentDictKey};
        return this.query(condition, args);
    }

    public List<ConfigDictPo> queryInByParentDictKeys(String parentDictKeys) {
        String columnValue = String.format("select %s from %s where %s in(%s)",
                ConfigDictPo.Id, this.tableName, ConfigDictPo.Key, parentDictKeys);
        String condition = Restrictions.in(ConfigDictPo.Pid, columnValue).toString();
        return this.query(condition);
    }

    public Map<String, ConfigDictPo> queryForMap(int pid) {
        return this.listToMap(this.queryByPid(pid));
    }

    public Map<String, ConfigDictPo> queryForMap(String parentDictKey) {
        return this.listToMap(this.queryByParentDictKey(parentDictKey));
    }

    private Map<String, ConfigDictPo> listToMap(List<ConfigDictPo> configDicts) {
        if (configDicts == null || configDicts.size() == 0) {
            return new HashMap<String, ConfigDictPo>(0);
        }

        Map<String, ConfigDictPo> configDictMap = new HashMap<>(configDicts.size());
        for (ConfigDictPo po : configDicts) {
            String key = po.getKey().trim().toLowerCase();
            if (!configDictMap.containsKey(key)) {
                configDictMap.put(key, po);
            }
        }

        return configDictMap;
    }
}