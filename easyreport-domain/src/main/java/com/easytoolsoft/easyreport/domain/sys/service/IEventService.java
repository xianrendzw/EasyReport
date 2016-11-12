package com.easytoolsoft.easyreport.domain.sys.service;

import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.domain.sys.example.EventExample;
import com.easytoolsoft.easyreport.domain.sys.po.Event;

/**
 * 系统事件或日志服务类
 *
 * @author Tom Deng
 */
public interface IEventService extends ICrudService<Event, EventExample> {
    void clear();

    void add(String source, String account, String message, String level, String url);
}