package com.easytoolsoft.easyreport.domain.metadata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.domain.metadata.example.ConfExample;
import com.easytoolsoft.easyreport.domain.metadata.example.GlobalParamExample;
import com.easytoolsoft.easyreport.domain.metadata.po.Conf;
import com.easytoolsoft.easyreport.domain.metadata.po.GlobalParam;
@Repository("EzrptMetaIGlobalParamDao")
public interface IGlobalParamDao extends ICrudDao<GlobalParam, GlobalParamExample>{
   
}