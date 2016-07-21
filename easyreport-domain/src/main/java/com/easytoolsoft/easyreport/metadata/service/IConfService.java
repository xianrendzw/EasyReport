package com.easytoolsoft.easyreport.metadata.service;

import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.metadata.po.Conf;

import java.util.Map;

/**
 * 报表相关配置项服务类
 *
 * @author Tom Deng
 */
public interface IConfService extends ICrudService<Conf> {
    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonColumns();

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonOptionalColumns();
}
