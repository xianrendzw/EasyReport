package com.easytoolsoft.easyreport.web.controller.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.meta.domain.ReportHistory;
import com.easytoolsoft.easyreport.meta.domain.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.meta.service.ReportHistoryService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表历史记录控制器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/report/history")
public class ReportHistoryController
    extends BaseController<ReportHistoryService, ReportHistory, ReportHistoryExample, Integer> {

    @RequestMapping(value = "/list")
    @OpLog(name = "查看报表版本历史")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> list(final DataGridPager pager, final Integer reportId) {
        final PageInfo pageInfo = pager.toPageInfo();
        final Map<String, Object> modelMap = new HashMap<>(2);
        final List<ReportHistory> list = this.service.getByPage(pageInfo, reportId == null ? 0 : reportId, null, null);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }
}