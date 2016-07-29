package com.easytoolsoft.easyreport.sys.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.helper.ParameterBuilder;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.sys.dao.IConfDao;
import com.easytoolsoft.easyreport.data.sys.po.Conf;
import com.easytoolsoft.easyreport.sys.service.IConfService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("EzrptSysConfService")
public class ConfService extends AbstractCrudService<IConfDao, Conf> implements IConfService {
    @Override
    public List<Conf> getByParentId(Integer parentId) {
        Map<String, Object> params = ParameterBuilder.getQueryParams(Conf.builder().parentId(parentId).build());
        return this.dao.select(params);
    }

    @Override
    public List<Conf> getByPage(PageInfo page, Integer parentId, String fieldName, String keyword) {
        Map<String, Object> where = ParameterBuilder.getQueryParams(Conf.builder().parentId(parentId).build());
        return this.getByPage(page, fieldName, keyword, where);
    }
}