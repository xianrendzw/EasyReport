package com.easytoolsoft.easyreport.domain.metadata.service;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.metadata.example.ConfExample;
import com.easytoolsoft.easyreport.data.metadata.po.Conf;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;

import java.util.List;
import java.util.Map;

/**
 * 报表相关配置项服务类
 *
 * @author Tom Deng
 */
public interface IConfService extends ICrudService<Conf, ConfExample> {
    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<Conf> getByPage(PageInfo pageInfo, Integer pid);

    /**
     * @param parentId
     * @return
     */
    List<Conf> getByParentId(Integer parentId);

    /**
     * @param key
     * @return
     */
    List<Conf> getByParentKey(String key);

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonColumns();

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonOptionalColumns();
}
