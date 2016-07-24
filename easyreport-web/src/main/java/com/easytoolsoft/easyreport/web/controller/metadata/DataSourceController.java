package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.metadata.po.DataSourcePo;
import com.easytoolsoft.easyreport.metadata.service.impl.DataSourceService;
import com.easytoolsoft.easyreport.web.common.JsonResult;
import com.easytoolsoft.easyreport.web.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public List<DataSourcePo> list(HttpServletRequest request) {
        return this.datasourceService.getAll(DataSourcePo.Id, DataSourcePo.Uid, DataSourcePo.Name);
    }

    @RequestMapping(value = "/query")

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

    public JsonResult remove(int id, HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "");

        try {
            this.datasourceService.removeById(id);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/batchRemove")

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