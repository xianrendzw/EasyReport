package com.easytoolsoft.easyreport.sys.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.sys.dao.IConfDao;
import com.easytoolsoft.easyreport.data.sys.example.ConfExample;
import com.easytoolsoft.easyreport.data.sys.po.Conf;
import com.easytoolsoft.easyreport.sys.service.IConfService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EzrptSysConfService")
public class ConfService
        extends AbstractCrudService<IConfDao, Conf, ConfExample>
        implements IConfService {

    @Override
    protected ConfExample getPageExample(String fieldName, String keyword) {
        ConfExample example = new ConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Conf> getByParentId(Integer parentId) {
        ConfExample example = new ConfExample();
        example.or().andParentIdEqualTo(parentId);
        return this.dao.selectByExample(example);
    }

    @Override
    public List<Conf> getByPage(PageInfo page, Integer parentId, String fieldName, String keyword) {
        ConfExample example = new ConfExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andFieldLike(fieldName, keyword);
        return this.getByPage(page, example);
    }
}