package com.easytoolsoft.easyreport.sys.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.sys.po.Conf;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统配置(ezrpt_sys_conf表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptSysIConfDao")
public interface IConfDao extends ICrudDao<Conf> {
}
