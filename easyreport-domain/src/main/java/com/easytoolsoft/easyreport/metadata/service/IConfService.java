package com.easytoolsoft.easyreport.metadata.service;

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
     * @param parentId
     * @return
     */
    List<Conf> getByParentId(Integer parentId);

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonColumns();

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonOptionalColumns();
}
