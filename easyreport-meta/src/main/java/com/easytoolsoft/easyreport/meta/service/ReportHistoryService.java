package com.easytoolsoft.easyreport.meta.service;

import java.util.List;

import com.easytoolsoft.easyreport.meta.domain.ReportHistory;
import com.easytoolsoft.easyreport.meta.domain.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 报表历史记录服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface ReportHistoryService extends CrudService<ReportHistory, ReportHistoryExample, Integer> {
    /**
     * @param page
     * @param reportId
     * @param fieldName
     * @param keyword
     * @return
     */
    List<ReportHistory> getByPage(PageInfo page, int reportId, String fieldName, String keyword);
}
