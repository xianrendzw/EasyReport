package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.metadata.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.ReportHistory;
import com.easytoolsoft.easyreport.domain.metadata.service.IReportHistoryService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表历史记录控制器
 */
@RestController
@RequestMapping(value = "/rest/metadata/history")
public class ReportHistoryController
        extends BaseController<IReportHistoryService, ReportHistory, ReportHistoryExample> {

    @RequestMapping(value = "/list")
    @OpLog(name = "查看报表版本历史")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> list(DataGridPager pager, Integer reportId) {
        PageInfo pageInfo = pager.toPageInfo();
        Map<String, Object> modelMap = new HashMap<>(2);
        List<ReportHistory> list = this.service.getByPage(pageInfo, reportId == null ? 0 : reportId, null, null);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }
}