package com.easytoolsoft.easyreport.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.engine.data.ColumnType;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.meta.data.ConfRepository;
import com.easytoolsoft.easyreport.meta.domain.Conf;
import com.easytoolsoft.easyreport.meta.domain.example.ConfExample;
import com.easytoolsoft.easyreport.meta.service.ConfService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("ConfService")
public class ConfServiceImpl
    extends AbstractCrudService<ConfRepository, Conf, ConfExample, Integer>
    implements ConfService {
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
    protected ConfExample getPageExample(final String fieldName, final String keyword) {
        final ConfExample example = new ConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Conf> getByPage(final PageInfo pageInfo, final Integer pid) {
        final ConfExample example = new ConfExample();
        example.or().andParentIdEqualTo(pid);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Conf> getByParentId(final Integer parentId) {
        return this.dao.selectByParentId(parentId);
    }

    @Override
    public List<Conf> getByParentKey(final String key) {
        return this.dao.selectByParentKey(key);
    }

    @Override
    public Map<String, ReportMetaDataColumn> getCommonColumns() {
        final Map<String, ReportMetaDataColumn> commonColumnMap =
            this.listToMap(this.getByParentKey(STAT_COLUMN), ColumnType.STATISTICAL, false);
        commonColumnMap.putAll(this.listToMap(this.getByParentKey(DATE_COLUMN), ColumnType.LAYOUT, false));
        commonColumnMap.putAll(this.listToMap(this.getByParentKey(DIM_COLUMN), ColumnType.DIMENSION, false));
        return commonColumnMap;
    }

    @Override
    public Map<String, ReportMetaDataColumn> getCommonOptionalColumns() {
        return this.listToMap(this.getByParentKey(OPTION_COLUMN), ColumnType.STATISTICAL, true);
    }

    private Map<String, ReportMetaDataColumn> listToMap(final List<Conf> confItems, final ColumnType type,
                                                        final boolean isOptional) {
        if (CollectionUtils.isEmpty(confItems)) {
            return new HashMap<>(0);
        }

        final Map<String, ReportMetaDataColumn> optionalColumnMap = new HashMap<>(confItems.size());
        for (final Conf confItem : confItems) {
            final String key = confItem.getKey().trim().toLowerCase();
            if (!optionalColumnMap.containsKey(key)) {
                final ReportMetaDataColumn metaDataColumn =
                    new ReportMetaDataColumn(confItem.getKey(), confItem.getValue(), type);
                metaDataColumn.setOptional(isOptional);
                optionalColumnMap.put(key, metaDataColumn);
            }
        }
        return optionalColumnMap;
    }
}
