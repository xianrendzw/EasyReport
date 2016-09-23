package com.easytoolsoft.easyreport.domain.metadata.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.metadata.dao.IConfDao;
import com.easytoolsoft.easyreport.data.metadata.example.ConfExample;
import com.easytoolsoft.easyreport.data.metadata.po.Conf;
import com.easytoolsoft.easyreport.domain.metadata.service.IConfService;
import com.easytoolsoft.easyreport.engine.data.ColumnType;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("EzrptMetaConfService")
public class ConfService
        extends AbstractCrudService<IConfDao, Conf, ConfExample>
        implements IConfService {
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

    @Override
    protected ConfExample getPageExample(String fieldName, String keyword) {
        ConfExample example = new ConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Conf> getByPage(PageInfo pageInfo, Integer pid) {
        ConfExample example = new ConfExample();
        example.or().andParentIdEqualTo(pid);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Conf> getByParentId(Integer parentId) {
        return this.dao.selectByParentId(parentId);
    }

    @Override
    public List<Conf> getByParentKey(String key) {
        return this.dao.selectByParentKey(key);
    }

    @Override
    public Map<String, ReportMetaDataColumn> getCommonColumns() {
        Map<String, ReportMetaDataColumn> commonColumnMap =
                this.listToMap(this.getByParentKey(STAT_COLUMN), ColumnType.STATISTICAL, false);
        commonColumnMap.putAll(this.listToMap(this.getByParentKey(DATE_COLUMN), ColumnType.LAYOUT, false));
        commonColumnMap.putAll(this.listToMap(this.getByParentKey(DIM_COLUMN), ColumnType.DIMENSION, false));
        return commonColumnMap;
    }

    @Override
    public Map<String, ReportMetaDataColumn> getCommonOptionalColumns() {
        return this.listToMap(this.getByParentKey(OPTION_COLUMN), ColumnType.STATISTICAL, true);
    }

    private Map<String, ReportMetaDataColumn> listToMap(List<Conf> confItems, ColumnType type, boolean isOptional) {
        if (CollectionUtils.isEmpty(confItems)) {
            return new HashMap<>(0);
        }

        Map<String, ReportMetaDataColumn> optionalColumnMap = new HashMap<>(confItems.size());
        for (Conf confItem : confItems) {
            String key = confItem.getKey().trim().toLowerCase();
            if (!optionalColumnMap.containsKey(key)) {
                ReportMetaDataColumn metaDataColumn =
                        new ReportMetaDataColumn(confItem.getKey(), confItem.getValue(), type);
                metaDataColumn.setOptional(isOptional);
                optionalColumnMap.put(key, metaDataColumn);
            }
        }
        return optionalColumnMap;
    }
}
