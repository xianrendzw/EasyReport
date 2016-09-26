package com.easytoolsoft.easyreport.domain.metadata.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.metadata.dao.IReportHistoryDao;
import com.easytoolsoft.easyreport.data.metadata.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.ReportHistory;
import com.easytoolsoft.easyreport.domain.metadata.service.IReportHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EzrptMetaReportHistoryService")
public class ReportHistoryService
        extends AbstractCrudService<IReportHistoryDao, ReportHistory, ReportHistoryExample>
        implements IReportHistoryService {

    @Override
    protected ReportHistoryExample getPageExample(String fieldName, String keyword) {
        ReportHistoryExample example = new ReportHistoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<ReportHistory> getByPage(PageInfo page, int reportId, String fieldName, String keyword) {
        ReportHistoryExample example = new ReportHistoryExample();
        ReportHistoryExample.Criteria criteria = example.or().andReportIdEqualTo(reportId);
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldLike(fieldName, keyword);
        }

        return this.getByPage(page, example);
    }
}
