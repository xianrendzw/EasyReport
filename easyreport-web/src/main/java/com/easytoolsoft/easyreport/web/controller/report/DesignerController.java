package com.easytoolsoft.easyreport.web.controller.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import com.easytoolsoft.easyreport.common.pair.IdNamePair;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.util.VelocityUtils;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.domain.ReportHistory;
import com.easytoolsoft.easyreport.meta.domain.example.ReportExample;
import com.easytoolsoft.easyreport.meta.domain.options.QueryParameterOptions;
import com.easytoolsoft.easyreport.meta.service.ConfService;
import com.easytoolsoft.easyreport.meta.service.ReportHistoryService;
import com.easytoolsoft.easyreport.meta.service.ReportService;
import com.easytoolsoft.easyreport.meta.service.TableReportService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.support.annotation.CurrentUser;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表设计器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/report/designer")
public class DesignerController
    extends BaseController<ReportService, Report, ReportExample, Integer> {
    @Resource
    private ReportHistoryService reportHistoryService;
    @Resource
    private TableReportService tableReportService;
    @Resource
    private ReportService dsService;
    @Resource
    private ConfService confService;

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取报表列表")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> list(final DataGridPager pager, final Integer id) {
        final PageInfo pageInfo = this.getPageInfo(pager);
        final List<Report> list = this.service.getByPage(pageInfo, "t1.category_id", id == null ? 0 : id);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @GetMapping(value = "/find")
    @OpLog(name = "分页查询报表")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> find(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = this.getPageInfo(pager);
        final List<Report> list = this.service.getByPage(pageInfo, "t1." + fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @GetMapping(value = "/getAll")
    @OpLog(name = "获取所有报表")
    @RequiresPermissions("report.designer:view")
    public List<IdNamePair> getAll(@CurrentUser final User loginUser) {
        final List<Report> reportList = this.service.getAll();
        if (CollectionUtils.isEmpty(reportList)) {
            return new ArrayList<>(0);
        }

        final List<IdNamePair> list = new ArrayList<>(reportList.size());
        list.addAll(reportList.stream()
            .map(report -> new IdNamePair(String.valueOf(report.getId()), report.getName()))
            .collect(Collectors.toList()));
        return list;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增报表")
    @RequiresPermissions("report.designer:add")
    public ResponseResult add(@CurrentUser final User loginUser, final Report po) {
        po.setCreateUser(loginUser.getAccount());
        po.setUid(UUID.randomUUID().toString());
        po.setComment("");
        po.setGmtCreated(new Date());
        this.service.add(po);
        this.reportHistoryService.add(this.getReportHistory(loginUser, po));
        return ResponseResult.success("");
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改报表")
    @RequiresPermissions("report.designer:edit")
    public ResponseResult edit(@CurrentUser final User loginUser, final Report po) {
        this.service.editById(po);
        this.reportHistoryService.add(this.getReportHistory(loginUser, po));
        return ResponseResult.success("");
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除报表")
    @RequiresPermissions("report.designer:remove")
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/execSqlText")
    @OpLog(name = "获取报表元数据列集合")
    @RequiresPermissions("report.designer:view")
    public ResponseResult execSqlText(final Integer dsId, String sqlText, Integer dataRange,
                                      final String queryParams, final HttpServletRequest request) {
        if (dsId != null) {
            if (dataRange == null) {
                dataRange = 7;
            }
            sqlText = this.getSqlText(sqlText, dataRange, queryParams, request);
            return ResponseResult.success(this.service.getMetaDataColumns(dsId, sqlText));
        }
        return ResponseResult.failure(10006, "没有选择数据源");
    }

    @PostMapping(value = "/previewSqlText")
    @OpLog(name = "预览报表SQL语句")
    @RequiresPermissions("report.designer:view")
    public ResponseResult previewSqlText(final Integer dsId, String sqlText, Integer dataRange,
                                         final String queryParams, final HttpServletRequest request) {
        if (dsId != null) {
            if (dataRange == null) {
                dataRange = 7;
            }
            sqlText = this.getSqlText(sqlText, dataRange, queryParams, request);
            this.service.explainSqlText(dsId, sqlText);
            return ResponseResult.success(sqlText);
        }
        return ResponseResult.failure(10006, "没有选择数据源");
    }

    @GetMapping(value = "/getMetaColumnScheme")
    @OpLog(name = "获取报表元数据列结构")
    @RequiresPermissions("report.designer:view")
    public ReportMetaDataColumn getMetaColumnScheme() {
        final ReportMetaDataColumn column = new ReportMetaDataColumn();
        column.setName("expr");
        column.setType(4);
        column.setDataType("DECIMAL");
        column.setWidth(42);
        return column;
    }

    private String getSqlText(final String sqlText, final Integer dataRange, final String queryParams,
                              final HttpServletRequest request) {
        final Map<String, Object> formParameters =
            this.tableReportService.getBuildInParameters(request.getParameterMap(), dataRange);
        if (StringUtils.isNotBlank(queryParams)) {
            final List<QueryParameterOptions> queryParameters = JSON.parseArray(queryParams,
                QueryParameterOptions.class);
            queryParameters.stream()
                .filter(parameter -> !formParameters.containsKey(parameter.getName()))
                .forEach(parameter -> formParameters.put(parameter.getName(), parameter.getRealDefaultValue()));
        }
        return VelocityUtils.parse(sqlText, formParameters);
    }

    private PageInfo getPageInfo(final DataGridPager pager) {
        final PageInfo pageInfo = pager.toPageInfo("t1.");
        if ("dsName".equals(pager.getSort())) {
            pageInfo.setSortItem("t1.ds_id");
        }
        return pageInfo;
    }

    private ReportHistory getReportHistory(@CurrentUser final User loginUser, final Report po) {
        return ReportHistory.builder()
            .reportId(po.getId())
            .categoryId(po.getCategoryId())
            .dsId(po.getDsId())
            .author(loginUser.getAccount())
            .comment(po.getComment())
            .name(po.getName())
            .uid(po.getUid())
            .metaColumns(po.getMetaColumns())
            .queryParams(po.getQueryParams())
            .options(po.getOptions())
            .sqlText(po.getSqlText())
            .status(po.getStatus())
            .sequence(po.getSequence())
            .gmtCreated(new Date())
            .gmtModified(new Date())
            .build();
    }
}