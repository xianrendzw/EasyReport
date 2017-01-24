package com.easytoolsoft.easyreport.domain.metadata.service.impl;

import org.springframework.stereotype.Service;

import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.domain.metadata.dao.IGlobalParamDao;
import com.easytoolsoft.easyreport.domain.metadata.example.GlobalParamExample;
import com.easytoolsoft.easyreport.domain.metadata.po.GlobalParam;
import com.easytoolsoft.easyreport.domain.metadata.service.IGlobalParamService;

@Service("EzrptMetaGlobalParamService")
public class GlobalParamService
        extends AbstractCrudService<IGlobalParamDao, GlobalParam, GlobalParamExample>
        implements IGlobalParamService {
    @Override
    protected GlobalParamExample getPageExample(String fieldName, String keyword) {
        GlobalParamExample example = new GlobalParamExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}
