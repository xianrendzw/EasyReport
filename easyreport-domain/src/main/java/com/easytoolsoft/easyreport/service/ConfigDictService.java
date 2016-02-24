package com.easytoolsoft.easyreport.service;

import com.easytoolsoft.easyreport.dao.ConfigDictDao;
import com.easytoolsoft.easyreport.data.jdbc.BaseService;
import com.easytoolsoft.easyreport.engine.data.ColumnType;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.po.ConfigDictPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置字典表服务类
 */
@Service
public class ConfigDictService extends BaseService<ConfigDictDao, ConfigDictPo> {

    /**
     * 统计列对应的配置字典表中的Key
     */
    private static final String STAT_COLUMN = "statColumn";

    /**
     * 日期列对应的配置字典表中的Key
     */
    private static final String DATE_COLUMN = "dateColumn";

    /**
     * 常见维度对应的配置字典表中的Key
     */
    private static final String DIM_COLUMN = "dimColumn";

    /**
     * 常见可选列对应的配置字典表中的Key
     */
    private static final String OPTION_COLUMN = "optionalColumn";

    @Autowired
    public ConfigDictService(ConfigDictDao dao) {
        super(dao);
    }

    public Map<String, ReportMetaDataColumn> getCommonColumns() {
        Map<String, ReportMetaDataColumn> commonColumnMap = this.listToMap(this.dao.queryByParentDictKey(STAT_COLUMN),
                ColumnType.STATISTICAL, false);
        commonColumnMap.putAll(this.listToMap(this.dao.queryByParentDictKey(DATE_COLUMN), ColumnType.LAYOUT, false));
        commonColumnMap.putAll(this.listToMap(this.dao.queryByParentDictKey(DIM_COLUMN), ColumnType.DIMENSION, false));
        return commonColumnMap;
    }

    public Map<String, ReportMetaDataColumn> getCommonOptionalColumns() {
        return this.listToMap(this.dao.queryByParentDictKey(OPTION_COLUMN), ColumnType.STATISTICAL, true);
    }

    private Map<String, ReportMetaDataColumn> listToMap(List<ConfigDictPo> configDicts, ColumnType type,
                                                        boolean isOptional) {
        if (configDicts == null || configDicts.size() == 0) {
            return new HashMap<String, ReportMetaDataColumn>(0);
        }

        Map<String, ReportMetaDataColumn> optionalColumnMap = new HashMap<>(configDicts.size());
        for (ConfigDictPo configDict : configDicts) {
            String key = configDict.getKey().trim().toLowerCase();
            if (!optionalColumnMap.containsKey(key)) {
                ReportMetaDataColumn metaDataColumn = new ReportMetaDataColumn(configDict.getKey(),
                        configDict.getValue(), type);
                metaDataColumn.setOptional(isOptional);
                optionalColumnMap.put(key, metaDataColumn);
            }
        }

        return optionalColumnMap;
    }
}