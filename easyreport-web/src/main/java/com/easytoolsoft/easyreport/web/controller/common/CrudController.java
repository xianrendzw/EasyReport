package com.easytoolsoft.easyreport.web.controller.common;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共CRUD Action控制器类
 *
 * @author Tom Deng
 */
@Slf4j
public class CrudController<Service extends ICrudService<Model>, Model> extends AbstractController {
    @Autowired
    protected Service service;

    @GetMapping(value = "/list")
    public Map<String, Object> list(DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Model> list = this.service.getByPage(pageInfo);
        return this.getListMap(pageInfo, list);
    }

    @GetMapping(value = "/find")
    public Map<String, Object> find(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Model> list = this.service.getByPage(pageInfo, fieldName, keyword);
        return this.getListMap(pageInfo, list);
    }


    @PostMapping(value = "/add")
    public JsonResult add(Model po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.service.add(po);
            this.logSuccessResult(result, po.toString(), req);
        } catch (Exception ex) {
            this.logExceptionResult(result, ex, po.toString(), req);
        }
        return result;
    }

    @PostMapping(value = "/edit")
    public JsonResult edit(Model po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.service.editById(po);
            this.logSuccessResult(result, po.toString(), req);
        } catch (Exception ex) {
            this.logExceptionResult(result, ex, po.toString(), req);
        }
        return result;
    }

    @PostMapping(value = "/remove")
    public JsonResult remove(int id, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.service.removeById(id);
            this.logSuccessResult(result, String.format("删除[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            this.logExceptionResult(result, ex, String.format("删除[ID:%s]操作失败!", id), req);
        }
        return result;
    }

    private Map<String, Object> getListMap(PageInfo pageInfo, List<Model> list) {
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }
}
