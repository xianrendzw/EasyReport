package com.easytoolsoft.easyreport.metadata.service;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.metadata.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.ReportHistory;

import java.util.List;

/**
 * 报表历史记录服务类
 *
 * @author Tom Deng
 */
public interface IReportHistoryService extends ICrudService<ReportHistory, ReportHistoryExample> {
    /**
     * @param page
     * @param reportId
     * @param fieldName
     * @param keyword
     * @return
     */
    List<ReportHistory> getByPage(PageInfo page, int reportId, String fieldName, String keyword);
}
