package com.easytoolsoft.easyreport.domain.sys.service.impl;

import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.domain.sys.dao.IEventDao;
import com.easytoolsoft.easyreport.domain.sys.example.EventExample;
import com.easytoolsoft.easyreport.domain.sys.po.Event;
import com.easytoolsoft.easyreport.domain.sys.service.IEventService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author tomdeng
 */
@Service("EzrptSysEventService")
public class EventService
        extends AbstractCrudService<IEventDao, Event, EventExample>
        implements IEventService {

    @Override
    protected EventExample getPageExample(String fieldName, String keyword) {
        EventExample example = new EventExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void clear() {
        this.dao.deleteByExample(null);
    }

    @Override
    public void add(String source, String account, String message, String level, String url) {
        Event event = Event.builder()
                .source(source)
                .account(account)
                .userId(-1)
                .message(message)
                .level(level)
                .url(url)
                .gmtCreated(new Date())
                .build();
        this.add(event);
    }
}