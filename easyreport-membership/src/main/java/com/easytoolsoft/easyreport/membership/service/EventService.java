package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.membership.domain.Event;
import com.easytoolsoft.easyreport.membership.domain.example.EventExample;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 系统事件或日志服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface EventService extends CrudService<Event, EventExample, Integer> {
    /**
     *
     */
    void clear();

    /**
     * @param source
     * @param account
     * @param message
     * @param level
     * @param url
     */
    void add(String source, String account, String message, String level, String url);
}