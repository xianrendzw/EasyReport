package com.easytoolsoft.easyreport.web.controller.metadata;

import com.alibaba.fastjson.JSON;
import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.util.VelocityUtils;
import com.easytoolsoft.easyreport.metadata.service.ICategoryService;
import com.easytoolsoft.easyreport.metadata.service.IConfService;
import com.easytoolsoft.easyreport.metadata.service.IDataSourceService;
import com.easytoolsoft.easyreport.metadata.service.IReportHistoryService;
import com.easytoolsoft.easyreport.metadata.service.IReportService;
import com.easytoolsoft.easyreport.report.ITableReportService;
import com.easytoolsoft.easyreport.metadata.vo.QueryParameter;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.apache.commons.lang3.StringUtils;
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
 * 报表设计器控制器
 */
@RestController
@RequestMapping(value = "/rest/metadata/report")
public class ReportController extends AbstractController {
    @Resource
    private IReportService reportService;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private ITableReportService tableReportService;
    @Resource
    private IDataSourceService dsService;
    @Resource
    private IReportHistoryService reportHistoryService;
    @Resource
    private IConfService confService;

    @RequestMapping(value = {"", "/", "/index"})
    public String home() {
        return "report/designer";
    }

    @RequestMapping(value = "/listChildNodes")
    public List<EasyUITreeNode<Category>> listChildNodes(Integer id) {
        if (id == null) {
            id = 0;
        }

        List<EasyUITreeNode<Category>> treeNodes = new ArrayList<>();
        try {
            List<Category> reportingPos = this.categoryService.getChildren(id);
            treeNodes.addAll(reportingPos.stream().map(this::createTreeNode).collect(Collectors.toList()));
        } catch (Exception ex) {
            this.logException(ex);
        }

        return treeNodes;
    }

    @RequestMapping(value = "/loadSqlColumns")
    public ParamJsonResult<List<ReportMetaDataColumn>> loadSqlColumns(Integer dsId, String sqlText, Integer dataRange,
                                                                      String jsonQueryParams, HttpServletRequest request) {
        ParamJsonResult<List<ReportMetaDataColumn>> result = new ParamJsonResult<List<ReportMetaDataColumn>>(false, "");
        if (dsId == null) {
            return result;
        }

        try {
            sqlText = this.getSqlText(sqlText, dataRange, jsonQueryParams, request);
            result.setData(this.reportService.getReportMetaDataColumns(dsId, sqlText));
            result.setSuccess(true);
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }
        return result;
    }

    @RequestMapping(value = "/viewSqlText")
    public ParamJsonResult<String> viewSqlText(Integer dsId, String sqlText, Integer dataRange, String jsonQueryParams,
                                               HttpServletRequest request) {
        ParamJsonResult<String> result = new ParamJsonResult<String>(false, "");
        if (dsId == null)
            return result;
        if (dataRange == null)
            dataRange = 7;

        try {
            sqlText = this.getSqlText(sqlText, dataRange, jsonQueryParams, request);
            this.reportService.explainSqlText(dsId, sqlText);
            result.setData(sqlText);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }
        return result;
    }

    private String getSqlText(String sqlText, Integer dataRange, String jsonQueryParams, HttpServletRequest request) {
        Map<String, Object> formParameters = tableReportService.getBuildInParameters(request.getParameterMap(),
                dataRange);
        if (StringUtils.isNotBlank(jsonQueryParams)) {
            List<QueryParameter> queryParams = JSON.parseArray(jsonQueryParams, QueryParameter.class);
            for (QueryParameter queryParam : queryParams) {
                if (formParameters.containsKey(queryParam.getName()))
                    continue;
                formParameters.put(queryParam.getName(), queryParam.getRealDefaultValue());
            }
        }
        return VelocityUtils.parse(sqlText, formParameters);
    }

    @RequestMapping(value = "/getSqlColumn")
    public ReportMetaDataColumn getSqlColumn() {
        ReportMetaDataColumn sqlColumnPo = new ReportMetaDataColumn();
        sqlColumnPo.setName("expr");
        sqlColumnPo.setType(4);
        sqlColumnPo.setDataType("DECIMAL");
        sqlColumnPo.setWidth(42);
        return sqlColumnPo;
    }

    @RequestMapping(value = "/getHistorySqlText")
    public Map<String, Object> getHistorySqlText(Integer page, Integer rows, Integer reportId,
                                                 HttpServletRequest request) {
        if (reportId == null)
            reportId = 0;
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 15;

        PageInfo pageInfo = new PageInfo((page - 1) * rows, rows);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try {
            List<ReportingSqlHistoryPo> list = this.reportHistoryService.getByPage(pageInfo, reportId);
            modelMap.put("total", pageInfo.getTotals());
            modelMap.put("rows", list);
        } catch (Exception ex) {
            this.logException(ex);
        }
        return modelMap;
    }

    @RequestMapping(value = "/addTreeNode")
    public ParamJsonResult<List<EasyUITreeNode<Report>>> addTreeNode(Report po, HttpServletRequest request) {
        ParamJsonResult<List<EasyUITreeNode<Report>>> result = new ParamJsonResult<List<EasyUITreeNode<Report>>>(false,
                "");

        try {
            po.setId(this.reportService.addReport(po));
            po = this.reportService.getById(po.getId());
            List<EasyUITreeNode<Report>> nodes = new ArrayList<EasyUITreeNode<Report>>();
            EasyUITreeNode<Report> treeNode = this.createTreeNode(po);
            nodes.add(treeNode);
            result.setData(nodes);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/editTreeNode")
    public ParamJsonResult<EasyUITreeNode<Report>> editTreeNode(Report po, HttpServletRequest request) {
        ParamJsonResult<EasyUITreeNode<Report>> result = new ParamJsonResult<EasyUITreeNode<Report>>(false, "");

        try {
            this.categoryService.editNode(po);
            po = this.reportService.getById(po.getId());
            EasyUITreeNode<Report> treeNode = this.createTreeNode(po);
            result.setData(treeNode);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }
        return result;
    }

    @RequestMapping(value = "/dragTreeNode")
    public JsonResult dragTreeNode(Integer sourceId, Integer targetId, Integer sourcePid, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "");

        try {
            this.categoryService.dragNode(sourceId, targetId, sourcePid);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }
        return result;
    }

    @RequestMapping(value = "/pasteTreeNode")
    public ParamJsonResult<List<EasyUITreeNode<Report>>> pasteTreeNode(Integer sourceId, Integer targetId,
                                                                            HttpServletRequest request) {
        ParamJsonResult<List<EasyUITreeNode<Report>>> result = new ParamJsonResult<List<EasyUITreeNode<Report>>>(false, "");

        if (sourceId == null || sourceId <= 0 || targetId == null || targetId < 0) {
            result.setMsg("提交的参数数据出错！");
            return result;
        }

        try {
            Report po = this.categoryService.pasteNode(sourceId, targetId, "");
            ReportingSqlHistoryPo historyPo = new ReportingSqlHistoryPo();
            historyPo.setReportId(po.getId());
            historyPo.setAuthor(po.getCreateUser());
            historyPo.setSqlText(po.getSqlText());
            this.reportHistoryService.add(historyPo);
            List<EasyUITreeNode<Report>> nodes = new ArrayList<EasyUITreeNode<Report>>();
            EasyUITreeNode<Report> treeNode = this.createTreeNode(po);
            nodes.add(treeNode);
            result.setData(nodes);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/cloneTreeNode")
    public JsonResult cloneTreeNode(Integer sourceId, Integer targetId, Integer dsId, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "");

        if (sourceId == null || sourceId <= 0 || targetId == null || targetId < 0) {
            result.setMsg("提交的参数数据出错！");
            return result;
        }

        if (dsId == null) {
            dsId = 0;
        }

        try {
            this.categoryService.cloneNode(sourceId, targetId, dsId);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/add")
    public ParamJsonResult<List<EasyUITreeNode<Report>>> add(Report po, HttpServletRequest request) {
        ParamJsonResult<List<EasyUITreeNode<Report>>> result = new ParamJsonResult<List<EasyUITreeNode<Report>>>(false, "");

        try {
            po.setCreateUser("");
            po.setComment("");
            po.setFlag(1);
            po.setId(this.reportService.addReport(po));
            ReportingSqlHistoryPo historyPo = new ReportingSqlHistoryPo();
            historyPo.setReportId(po.getId());
            historyPo.setAuthor(po.getCreateUser());
            historyPo.setSqlText(po.getSqlText());
            this.reportHistoryService.add(historyPo);
            po = this.reportService.getById(po.getId());
            List<EasyUITreeNode<Report>> nodes = new ArrayList<EasyUITreeNode<Report>>();
            EasyUITreeNode<Report> treeNode = this.createTreeNode(po);
            nodes.add(treeNode);
            result.setData(nodes);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/edit")
    public ParamJsonResult<EasyUITreeNode<Report>> edit(Report po, Boolean isChange, HttpServletRequest request) {
        ParamJsonResult<EasyUITreeNode<Report>> result = new ParamJsonResult<EasyUITreeNode<Report>>(false, "");

        try {
            this.reportService.editReport(po);
            if (isChange == null || isChange) {
                ReportingSqlHistoryPo historyPo = new ReportingSqlHistoryPo();
                historyPo.setReportId(po.getId());
                historyPo.setAuthor(po.getCreateUser());
                historyPo.setSqlText(po.getSqlText());
                this.reportHistoryService.add(historyPo);
            }
            EasyUITreeNode<Report> treeNode = this.createTreeNode(this.reportService.getById(po.getId()));
            result.setData(treeNode);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(Integer id, Integer pid, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "");

        try {
            if (!this.reportService.hasChild(id)) {
                this.reportService.remove(id, pid);
                this.setSuccessResult(result, "");
            } else {
                this.setFailureResult(result, "操作失败！当前节点还有子节点，请先删除子节点！");
            }
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }
        return result;
    }

    @RequestMapping(value = "/search")
    public Map<String, Object> search(Integer page, Integer rows, String fieldName, String keyword,
                                      HttpServletRequest request) {
        if (page == null || page < 1)
            page = 1;
        if (rows == null)
            rows = 15;

        PageInfo pageInfo = new PageInfo((page - 1) * rows, rows);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try {
            List<Report> list = this.reportService.getByPage(fieldName, keyword, pageInfo);
            modelMap.put("total", pageInfo.getTotals());
            modelMap.put("rows", list);
        } catch (Exception ex) {
            this.logException(ex);
        }
        return modelMap;
    }

    @RequestMapping(value = "/setQueryParam")
    public ParamJsonResult<EasyUITreeNode<Report>> setQueryParam(Integer id, String jsonQueryParams,
                                                                      HttpServletRequest request) {
        ParamJsonResult<EasyUITreeNode<Report>> result = new ParamJsonResult<EasyUITreeNode<Report>>(false, "");

        if (id == null) {
            this.setFailureResult(result, "您没有选择报表节点");
            return result;
        }

        try {
            this.reportService.setQueryParams(id, jsonQueryParams);
            Report po = this.reportService.getById(id);
            EasyUITreeNode<Report> treeNode = this.createTreeNode(po);
            result.setData(treeNode);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    private EasyUITreeNode<Report> createTreeNode(Report po) {
        String configId = Integer.toString(po.getId());
        String text = po.getName();
        String state = po.getHasChild() ? "closed" : "open";
        return new EasyUITreeNode<>(configId, text, state, po);
    }

    @RequestMapping(value = "/query")
    public Map<String, Object> query(Integer page, Integer rows, HttpServletRequest request) {
        if (page == null || page < 1)
            page = 1;
        if (rows == null)
            rows = 30;

        List<Report> list = null;
        PageInfo pageInfo = new PageInfo((page - 1) * rows, rows);
        list = this.reportService.getByPage(pageInfo);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);

        return modelMap;
    }

    @RequestMapping(value = "/listAllChildNodes")
    public List<EasyUITreeNode<Report>> listAllChildNodes() {
        List<EasyUITreeNode<Report>> roots = new ArrayList<EasyUITreeNode<Report>>();

        try {
            List<Report> pos = this.reportService.getAll();
            for (Report po : pos) {
                if (po.getPid() == 0) {
                    String configId = Integer.toString(po.getId());
                    String text = po.getName();
                    String state = po.getHasChild() ? "closed" : "open";
                    EasyUITreeNode<Report> parentNode = new EasyUITreeNode<Report>(configId, text, state, null);
                    this.loadChildNodes(pos, parentNode);
                    roots.add(parentNode);
                }
            }
        } catch (Exception ex) {
            this.logException(ex);
        }

        return roots;
    }

    private void loadChildNodes(List<Report> pos, EasyUITreeNode<Report> parentNode) {
        int id = Integer.valueOf(parentNode.getId());
        for (Report po : pos) {
            if (po.getPid() == id) {
                String configId = Integer.toString(po.getId());
                String text = po.getName();
                String state = po.getHasChild() ? "closed" : "open";
                EasyUITreeNode<Report> childNode = new EasyUITreeNode<Report>(configId, text, state, null);
                this.loadChildNodes(pos, childNode);
                parentNode.getChildren().add(childNode);
            }
        }
    }
}