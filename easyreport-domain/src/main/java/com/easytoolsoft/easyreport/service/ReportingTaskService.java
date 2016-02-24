package com.easytoolsoft.easyreport.service;

import com.easytoolsoft.easyreport.dao.ReportingTaskDao;
import com.easytoolsoft.easyreport.data.jdbc.BaseService;
import com.easytoolsoft.easyreport.po.ReportingTaskPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报表任务信息表服务类
 */
@Service
public class ReportingTaskService extends BaseService<ReportingTaskDao, ReportingTaskPo> {
    @Autowired
    public ReportingTaskService(ReportingTaskDao dao) {
        super(dao);
    }
}