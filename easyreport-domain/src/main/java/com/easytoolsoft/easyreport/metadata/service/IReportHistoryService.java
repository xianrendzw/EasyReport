package com.easytoolsoft.easyreport.metadata.service;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.metadata.po.ReportHistory;

import java.util.List;

/**
 * 报表历史记录服务类
 *
 * @author Tom Deng
 */
public interface IReportHistoryService extends ICrudService<ReportHistory> {
    /**
     * @param page
     * @param reportId
     * @param fieldName
     * @param keyword
     * @return
     */
    List<ReportHistory> getByPage(PageInfo page, int reportId, String fieldName, String keyword);
}
