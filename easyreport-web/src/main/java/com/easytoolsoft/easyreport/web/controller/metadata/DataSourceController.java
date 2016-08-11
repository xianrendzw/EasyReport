package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.metadata.example.DataSourceExample;
import com.easytoolsoft.easyreport.data.metadata.po.DataSource;
import com.easytoolsoft.easyreport.metadata.service.IDataSourceService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 报表数据源控制器
 */
@Controller
@RequestMapping(value = "/rest/metadata/ds")
public class DataSourceController
        extends BaseController<IDataSourceService, DataSource, DataSourceExample> {

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "report/dataSource";
    }

    @RequestMapping(value = "/listAll")
    public List<DataSource> listAll() {
        return this.service.getAll().stream()
                .map(x -> DataSource.builder()
                        .id(x.getId())
                        .uid(x.getUid())
                        .name(x.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<DataSource> list = this.service.getByPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(DataSource po) {
        JsonResult<String> result = new JsonResult<>();
        po.setUid(UUID.randomUUID().toString());
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(DataSource po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/testConnection")
    public JsonResult testConnection(String url, String pass, String user) throws SQLException {
        JsonResult<String> result = new JsonResult<>();
        result.setSuccess(this.service.testConnection(url, user, pass));
        return result;
    }

    @RequestMapping(value = "/testConnectionById")
    public JsonResult testConnection(Integer id) throws SQLException {
        JsonResult<String> result = new JsonResult<>();
        DataSource dsPo = this.service.getById(id);
        result.setSuccess(this.service.testConnection(
                dsPo.getJdbcUrl(),
                dsPo.getUser(), dsPo.getPassword()));
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }
}