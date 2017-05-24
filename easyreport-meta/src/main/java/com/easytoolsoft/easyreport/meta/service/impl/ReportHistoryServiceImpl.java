package com.easytoolsoft.easyreport.meta.service.impl;

import java.util.List;

import com.easytoolsoft.easyreport.meta.data.ReportHistoryRepository;
import com.easytoolsoft.easyreport.meta.domain.ReportHistory;
import com.easytoolsoft.easyreport.meta.domain.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.meta.service.ReportHistoryService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("ReportHistoryService")
public class ReportHistoryServiceImpl
    extends AbstractCrudService<ReportHistoryRepository, ReportHistory, ReportHistoryExample, Integer>
    implements ReportHistoryService {

    @Override
    protected ReportHistoryExample getPageExample(final String fieldName, final String keyword) {
        final ReportHistoryExample example = new ReportHistoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<ReportHistory> getByPage(final PageInfo page, final int reportId, final String fieldName,
                                         final String keyword) {
        final ReportHistoryExample example = new ReportHistoryExample();
        final ReportHistoryExample.Criteria criteria = example.or().andReportIdEqualTo(reportId);
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldLike(fieldName, keyword);
        }

        return this.getByPage(page, example);
    }
}
