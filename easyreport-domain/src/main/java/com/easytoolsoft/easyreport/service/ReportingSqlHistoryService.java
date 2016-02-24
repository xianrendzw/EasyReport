package com.easytoolsoft.easyreport.service;

import com.easytoolsoft.easyreport.dao.ReportingSqlHistoryDao;
import com.easytoolsoft.easyreport.data.PageInfo;
import com.easytoolsoft.easyreport.data.jdbc.BaseService;
import com.easytoolsoft.easyreport.po.ReportingSqlHistoryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务类
 */
@Service
public class ReportingSqlHistoryService extends BaseService<ReportingSqlHistoryDao, ReportingSqlHistoryPo> {
    @Autowired
    public ReportingSqlHistoryService(ReportingSqlHistoryDao dao) {
        super(dao);
    }

    public List<ReportingSqlHistoryPo> getByPage(PageInfo page, int reportId) {
        return this.dao.queryByPage(page, reportId);
    }
}