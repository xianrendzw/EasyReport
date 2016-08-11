package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.metadata.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.ReportHistory;
import com.easytoolsoft.easyreport.metadata.service.IReportHistoryService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表历史记录控制器
 */
@Controller
@RequestMapping(value = "/rest/metadata/reporthistory")
public class ReportHistoryController
        extends BaseController<IReportHistoryService, ReportHistory, ReportHistoryExample> {

    @RequestMapping(value = "/list")
    public Map<String, Object> list(DataGridPager pager, Integer reportId) {
        PageInfo pageInfo = pager.toPageInfo();
        Map<String, Object> modelMap = new HashMap<>(2);
        List<ReportHistory> list = this.service.getByPage(pageInfo, reportId, null, null);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }
}