package com.easytoolsoft.easyreport.web.controller.common;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共Action控制器类
 *
 * @author Tom Deng
 */
@Slf4j
public class BaseController<Service extends ICrudService<Model, Example>, Model, Example> {
    @Autowired
    protected Service service;

    public Map<String, Object> list(DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Model> list = this.service.getByPage(pageInfo);
        return this.getListMap(pageInfo, list);
    }

    public Map<String, Object> find(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Model> list = this.service.getByPage(pageInfo, fieldName, keyword);
        return this.getListMap(pageInfo, list);
    }

    public JsonResult add(Model po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.add(po);
        return result;
    }

    public JsonResult edit(Model po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    private Map<String, Object> getListMap(PageInfo pageInfo, List<Model> list) {
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }
}
