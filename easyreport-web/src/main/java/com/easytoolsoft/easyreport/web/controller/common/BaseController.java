package com.easytoolsoft.easyreport.web.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 公共Action控制器类
 *
 * @param <Service>
 * @param <Model>
 * @param <Example>
 * @param <Type>
 * @author Tom Deng
 * @date 2017-03-25
 */
@Slf4j
public class BaseController<Service extends CrudService<Model, Example, Type>, Model, Example, Type> {
    @Autowired
    protected Service service;

    protected ResponseResult create(final Model po) {
        return ResponseResult.success(this.service.add(po));
    }

    protected ResponseResult delete(final Type id) {
        return ResponseResult.success(this.service.removeById(id));
    }

    protected ResponseResult update(final Model po) {
        return ResponseResult.success(this.service.editById(po));
    }

    protected Map<String, Object> queryByPage(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Model> list = this.service.getByPage(pageInfo, fieldName, keyword);
        return this.getPageListMap(pageInfo, list);
    }

    protected Map<String, Object> getPageListMap(final PageInfo pageInfo, final List<Model> list) {
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }
}
