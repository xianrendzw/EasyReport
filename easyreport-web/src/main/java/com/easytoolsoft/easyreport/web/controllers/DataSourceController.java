package com.easytoolsoft.easyreport.web.controllers;

import com.easytoolsoft.easyreport.common.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.data.PageInfo;
import com.easytoolsoft.easyreport.po.DataSourcePo;
import com.easytoolsoft.easyreport.service.DataSourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 报表数据源控制器
 */
@Controller
@RequestMapping(value = "report/ds")
public class DataSourceController extends AbstractController {
    @Resource
    private DataSourceService datasourceService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "report/dataSource";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<DataSourcePo> list(HttpServletRequest request) {
        return this.datasourceService.getAll(DataSourcePo.Id, DataSourcePo.Uid, DataSourcePo.Name);
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public Map<String, Object> query(Integer page, Integer rows, HttpServletRequest request) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;

        PageInfo pageInfo = new PageInfo((page - 1) * rows, rows);
        String[] columnNames = new String[]{
                DataSourcePo.Id, DataSourcePo.Name, DataSourcePo.Uid,
                DataSourcePo.JdbcUrl, DataSourcePo.CreateUser, DataSourcePo.CreateTime};
        List<DataSourcePo> list = this.datasourceService.getByPage(pageInfo, columnNames);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public JsonResult add(DataSourcePo po, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "");

        try {
            po.setUid(UUID.randomUUID().toString());
            this.datasourceService.add(po);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public JsonResult edit(DataSourcePo po, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "");

        try {
            String[] columnNames = new String[]{
                    DataSourcePo.Name, DataSourcePo.User,
                    DataSourcePo.Password, DataSourcePo.JdbcUrl};
            this.datasourceService.edit(po, columnNames);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/testConnection")
    @ResponseBody
    public JsonResult testConnection(String url, String pass, String user) {
        JsonResult result = new JsonResult(false, "");

        try {
            result.setSuccess(this.datasourceService.getDao().testConnection(url, user, pass));
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/testConnectionById")
    @ResponseBody
    public JsonResult testConnection(Integer id) {
        JsonResult result = new JsonResult(false, "");

        try {
            DataSourcePo dsPo = this.datasourceService.getById(id);
            result.setSuccess(this.datasourceService.getDao().testConnection(dsPo.getJdbcUrl(), dsPo.getUser(), dsPo.getPassword()));
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult remove(int id, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "");

        try {
            this.datasourceService.remove(id);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/batchRemove")
    @ResponseBody
    public JsonResult remove(String ids, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "您没有权限执行该操作!");

        try {
            this.datasourceService.remove(ids);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }
}