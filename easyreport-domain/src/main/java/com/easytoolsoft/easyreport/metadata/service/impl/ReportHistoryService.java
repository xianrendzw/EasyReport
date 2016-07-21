package com.easytoolsoft.easyreport.metadata.service.impl;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.helper.ParameterBuilder;
import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.metadata.dao.IReportHistoryDao;
import com.easytoolsoft.easyreport.metadata.po.ReportHistory;
import com.easytoolsoft.easyreport.metadata.service.IReportHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("EzrptMetaReportHistoryService")
public class ReportHistoryService extends AbstractCrudService<IReportHistoryDao, ReportHistory>
        implements IReportHistoryService {

    @Override
    public List<ReportHistory> getByPage(PageInfo page, int reportId, String fieldName, String keyword) {
        Map<String, Object> where = ParameterBuilder.getQueryParams(
                ReportHistory.builder().reportId(reportId).build());
        return this.getByPage(page, fieldName, keyword, where);
    }
}
