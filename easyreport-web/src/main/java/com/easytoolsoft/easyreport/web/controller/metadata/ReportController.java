package com.easytoolsoft.easyreport.web.controller.metadata;

import com.alibaba.fastjson.JSON;
import com.easytoolsoft.easyreport.common.pair.IdNamePair;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.data.metadata.example.ReportExample;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import com.easytoolsoft.easyreport.data.metadata.po.ReportHistory;
import com.easytoolsoft.easyreport.domain.metadata.service.IConfService;
import com.easytoolsoft.easyreport.domain.metadata.service.IReportHistoryService;
import com.easytoolsoft.easyreport.domain.metadata.service.IReportService;
import com.easytoolsoft.easyreport.domain.metadata.vo.QueryParameter;
import com.easytoolsoft.easyreport.domain.report.ITableReportService;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.util.VelocityUtils;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 报表元数据设置控件器
 */
@RestController
@RequestMapping(value = "/rest/metadata/report")
public class ReportController
        extends BaseController<IReportService, Report, ReportExample> {
    @Resource
    private IReportHistoryService reportHistoryService;
    @Resource
    private ITableReportService tableReportService;
    @Resource
    private IReportService dsService;
    @Resource
    private IConfService confService;

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取报表列表")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> list(DataGridPager pager, Integer id) {
        PageInfo pageInfo = this.getPageInfo(pager);
        List<Report> list = this.service.getByPage(pageInfo, "t1.category_id", id == null ? 0 : id);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @GetMapping(value = "/find")
    @OpLog(name = "分页查询报表")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> find(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = this.getPageInfo(pager);
        List<Report> list = this.service.getByPage(pageInfo, "t1." + fieldName, "%" + keyword + "%");
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @GetMapping(value = "/getAll")
    @OpLog(name = "获取所有报表")
    @RequiresPermissions("report.designer:view")
    public List<IdNamePair> getAll(@CurrentUser User loginUser) {
        List<Report> reportList = this.service.getAll();
        if (CollectionUtils.isEmpty(reportList)) {
            return new ArrayList<>(0);
        }

        List<IdNamePair> list = new ArrayList<>(reportList.size());
        list.addAll(reportList.stream()
                .map(report -> new IdNamePair(String.valueOf(report.getId()), report.getName()))
                .collect(Collectors.toList()));
        return list;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增报表")
    @RequiresPermissions("report.designer:add")
    public JsonResult add(Report po) {
        JsonResult<String> result = new JsonResult<>();
        po.setCreateUser("");
        po.setComment("");
        this.service.add(po);
        ReportHistory historyPo = ReportHistory.builder()
                .reportId(po.getId())
                .build();
        this.reportHistoryService.add(historyPo);
        return result;
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改报表")
    @RequiresPermissions("report.designer:edit")
    public JsonResult edit(Report po, Boolean isChange) {
        JsonResult result = new JsonResult<>();
        this.service.editById(po);
        if (isChange == null || isChange) {
            ReportHistory historyPo = new ReportHistory();
            historyPo.setReportId(po.getId());
            historyPo.setAuthor(po.getCreateUser());
            historyPo.setSqlText(po.getSqlText());
            this.reportHistoryService.add(historyPo);
        }
        return result;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除报表")
    @RequiresPermissions("report.designer:remove")
    public JsonResult remove(Integer id) {
        JsonResult result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @PostMapping(value = "/execSqlText")
    @OpLog(name = "获取报表元数据列集合")
    @RequiresPermissions("report.designer:view")
    public JsonResult execSqlText(Integer dsId, String sqlText, Integer dataRange,
                                  String queryParams, HttpServletRequest request) {
        JsonResult<List<ReportMetaDataColumn>> result = new JsonResult<>();
        if (dsId == null) {
            return result;
        }
        sqlText = this.getSqlText(sqlText, dataRange, queryParams, request);
        result.setData(this.service.getMetaDataColumns(dsId, sqlText));
        return result;
    }

    @GetMapping(value = "/previewSqlText")
    @OpLog(name = "预览报表SQL语句")
    @RequiresPermissions("report.designer:view")
    public JsonResult previewSqlText(Integer dsId, String sqlText, Integer dataRange, String queryParams,
                                     HttpServletRequest request) {
        JsonResult<String> result = new JsonResult<>();
        if (dsId == null) return result;
        if (dataRange == null) dataRange = 7;
        sqlText = this.getSqlText(sqlText, dataRange, queryParams, request);
        this.service.explainSqlText(dsId, sqlText);
        result.setData(sqlText);
        return result;
    }

    @GetMapping(value = "/getSqlColumnScheme")
    @OpLog(name = "获取报表元数据列结构")
    @RequiresPermissions("report.designer:view")
    public ReportMetaDataColumn getSqlColumnScheme() {
        ReportMetaDataColumn column = new ReportMetaDataColumn();
        column.setName("expr");
        column.setType(4);
        column.setDataType("DECIMAL");
        column.setWidth(42);
        return column;
    }

    private String getSqlText(String sqlText, Integer dataRange, String queryParams, HttpServletRequest request) {
        Map<String, Object> formParameters = tableReportService.getBuildInParameters(
                request.getParameterMap(), dataRange);
        if (StringUtils.isNotBlank(queryParams)) {
            List<QueryParameter> queryParameters = JSON.parseArray(queryParams, QueryParameter.class);
            queryParameters.stream().filter(parameter ->
                    !formParameters.containsKey(parameter.getName()))
                    .forEach(parameter -> {
                        formParameters.put(parameter.getName(), parameter.getRealDefaultValue());
                    });
        }
        return VelocityUtils.parse(sqlText, formParameters);
    }

    private PageInfo getPageInfo(DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo("t1.");
        if ("dsName".equals(pager.getSort())) {
            pageInfo.setSortItem("t1.ds_id");
        }
        return pageInfo;
    }
}