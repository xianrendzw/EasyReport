package com.easytoolsoft.easyreport.sys.service;

import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.sys.po.Event;

/**
 * 系统事件或日志服务类
 *
 * @author Tom Deng
 */
public interface IEventService extends ICrudService<Event> {
    void clear();
}