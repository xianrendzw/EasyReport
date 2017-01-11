package com.easytoolsoft.easyreport.domain.metadata.service;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.domain.metadata.example.GlobalParamExample;
import com.easytoolsoft.easyreport.domain.metadata.po.GlobalParam;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;

import java.util.List;
import java.util.Map;

/**
 * 报表相关配置项服务类
 *
 * @author Tom Deng
 */
public interface IGlobalParamService extends ICrudService<GlobalParam, GlobalParamExample> {
    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<GlobalParam> getByPage(PageInfo pageInfo, Integer pid);

    /**
     * @param parentId
     * @return
     */
    List<GlobalParam> getByParentId(Integer parentId);

    /**
     * @param key
     * @return
     */
    List<GlobalParam> getByParentKey(String key);

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonColumns();

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonOptionalColumns();
}
