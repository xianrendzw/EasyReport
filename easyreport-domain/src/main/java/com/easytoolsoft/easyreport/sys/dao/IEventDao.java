package com.easytoolsoft.easyreport.sys.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.sys.po.Event;
import org.springframework.stereotype.Repository;

/**
 * 系统事件或日志(ezrpt_sys_event表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptSysIEventDao")
public interface IEventDao extends ICrudDao<Event> {
}
