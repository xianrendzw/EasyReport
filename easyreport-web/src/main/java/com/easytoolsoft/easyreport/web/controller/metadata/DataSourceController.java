package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.metadata.po.DataSource;
import com.easytoolsoft.easyreport.metadata.service.IDataSourceService;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class DataSourceController extends AbstractController {
    @Resource
    private IDataSourceService dsService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "report/dataSource";
    }

    @RequestMapping(value = "/listAll")
    public List<DataSource> listAll(HttpServletRequest req) {
        return this.dsService.getAll().stream()
                .map(x -> DataSource.builder()
                        .id(x.getId())
                        .uid(x.getUid())
                        .name(x.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(DataGridPager pager, String fieldName, String keyword,
                                    HttpServletRequest req) {
        PageInfo pageInfo = pager.toPageInfo();
        List<DataSource> list = this.dsService.getByPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(DataSource po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            po.setUid(UUID.randomUUID().toString());
            this.dsService.add(po);
            this.logSuccessResult(result, String.format("修改数据源[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改数据源:[%s]操作失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(DataSource po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.dsService.editById(po);
            this.logSuccessResult(result, String.format("修改数据源[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改数据源:[%s]操作失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/testConnection")
    public JsonResult testConnection(String url, String pass, String user, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            result.setSuccess(this.dsService.testConnection(url, user, pass));
        } catch (Exception ex) {
            this.logExceptionResult(result, ex, req);
        }

        return result;
    }

    @RequestMapping(value = "/testConnectionById")
    public JsonResult testConnection(Integer id, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            DataSource dsPo = this.dsService.getById(id);
            result.setSuccess(this.dsService.testConnection(
                    dsPo.getJdbcUrl(),
                    dsPo.getUser(), dsPo.getPassword()));
        } catch (Exception ex) {
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.dsService.removeById(id);
            this.logSuccessResult(result, String.format("修改数据源[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改数据源:[%s]操作失败!", id));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }
}