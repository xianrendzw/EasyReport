package com.easytoolsoft.easyreport.domain.metadata.service.impl;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.domain.metadata.dao.IConfDao;
import com.easytoolsoft.easyreport.domain.metadata.dao.IGlobalParamDao;
import com.easytoolsoft.easyreport.domain.metadata.example.ConfExample;
import com.easytoolsoft.easyreport.domain.metadata.example.GlobalParamExample;
import com.easytoolsoft.easyreport.domain.metadata.po.Conf;
import com.easytoolsoft.easyreport.domain.metadata.po.GlobalParam;
import com.easytoolsoft.easyreport.domain.metadata.service.IConfService;
import com.easytoolsoft.easyreport.domain.metadata.service.IGlobalParamService;
import com.easytoolsoft.easyreport.engine.data.ColumnType;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("EzrptMetaGlobalParamService")
public class GlobalParamService
        extends AbstractCrudService<IGlobalParamDao, GlobalParam, GlobalParamExample>
        implements IGlobalParamService {
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
    protected GlobalParamExample getPageExample(String fieldName, String keyword) {
        GlobalParamExample example = new GlobalParamExample();
//        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    @Override
    public List<GlobalParam> getByPage(PageInfo pageInfo, Integer pid) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<GlobalParam> getByParentId(Integer parentId) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<GlobalParam> getByParentKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Map<String, ReportMetaDataColumn> getCommonColumns() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Map<String, ReportMetaDataColumn> getCommonOptionalColumns() {
        // TODO Auto-generated method stub
        return null;
    }
}
