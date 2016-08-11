package com.easytoolsoft.easyreport.sys.service;

import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.sys.example.EventExample;
import com.easytoolsoft.easyreport.data.sys.po.Event;

/**
 * 系统事件或日志服务类
 *
 * @author Tom Deng
 */
public interface IEventService extends ICrudService<Event, EventExample> {
    void clear();
}