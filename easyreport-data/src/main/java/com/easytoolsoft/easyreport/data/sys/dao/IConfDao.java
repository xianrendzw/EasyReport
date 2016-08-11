package com.easytoolsoft.easyreport.data.sys.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.sys.example.ConfExample;
import com.easytoolsoft.easyreport.data.sys.po.Conf;
import org.springframework.stereotype.Repository;

/**
 * 系统配置(ezrpt_sys_conf表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptSysIConfDao")
public interface IConfDao extends ICrudDao<Conf, ConfExample> {
}
