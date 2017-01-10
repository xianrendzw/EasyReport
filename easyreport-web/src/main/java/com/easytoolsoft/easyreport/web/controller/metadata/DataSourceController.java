package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.domain.metadata.example.DataSourceExample;
import com.easytoolsoft.easyreport.domain.metadata.po.DataSource;
import com.easytoolsoft.easyreport.domain.metadata.service.IDataSourceService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 报表数据源控制器
 */
@RestController
@RequestMapping(value = "/rest/metadata/ds")
public class DataSourceController
        extends BaseController<IDataSourceService, DataSource, DataSourceExample> {

    @GetMapping(value = "/listAll")
    @OpLog(name = "获取所有数据源")
    @RequiresPermissions("report.ds:view")
    public List<DataSource> listAll() {
        List<DataSource> list = this.service.getAll();
        for(DataSource ds : list){
        	ds.decrypt();
        }
        return list;
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取数据源列表")
    @RequiresPermissions("report.ds:view")
    public Map<String, Object> list(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<DataSource> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        for(DataSource ds : list){
        	ds.decrypt();
        }
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增数据源")
    @RequiresPermissions("report.ds:add")
    public JsonResult add(DataSource po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setUid(UUID.randomUUID().toString());
        po.setJdbcUrl(po.getJdbcUrl());
        po.setUser(po.getUser());
        po.setPassword(po.getPassword());
        po.encrypt();
        this.service.add(po);
        return result;
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑数据源")
    @RequiresPermissions("report.ds:edit")
    public JsonResult edit(DataSource po) {
        JsonResult<String> result = new JsonResult<>();
        po.encrypt();
        this.service.editById(po);
        return result;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除数据源")
    @RequiresPermissions("report.ds:remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @PostMapping(value = "/testConnection")
    @OpLog(name = "测试数据源")
    @RequiresPermissions("report.ds:view")
    public JsonResult testConnection(String driverClass, String url, String pass, String user) {
        JsonResult<String> result = new JsonResult<>();
        result.setSuccess(this.service.testConnection(driverClass, url, user, pass));
        return result;
    }

    @PostMapping(value = "/testConnectionById")
    @OpLog(name = "测试数据源")
    @RequiresPermissions("report.ds:view")
    public JsonResult testConnection(Integer id) {
        JsonResult<String> result = new JsonResult<>();
        DataSource dsPo = this.service.getById(id);
        dsPo.decrypt();
        result.setSuccess(this.service.testConnection(
                dsPo.getDriverClass(),
                dsPo.getJdbcUrl(),
                dsPo.getUser(), dsPo.getPassword()));
        return result;
    }
}