package com.easytoolsoft.easyreport.domain.sys.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.domain.sys.example.ConfExample;
import com.easytoolsoft.easyreport.domain.sys.po.Conf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统配置(ezrpt_sys_conf表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptSysIConfDao")
public interface IConfDao extends ICrudDao<Conf, ConfExample> {

    List<Conf> selectByParentId(Integer parentId);

    List<Conf> selectByParentKey(@Param(value = "key") String key);
}
