package com.easytoolsoft.easyreport.sys.service.impl;

import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.sys.dao.IEventDao;
import com.easytoolsoft.easyreport.sys.po.Event;
import com.easytoolsoft.easyreport.sys.service.IEventService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("EzrptSysEventService")
public class EventService extends AbstractCrudService<IEventDao, Event> implements IEventService {
    @Override
    public void add(String source, String message, int userId, String account, String level) {
        Event event = new Event();
        event.setSource(source);
        event.setMessage(message);
        event.setUserId(userId);
        event.setAccount(account);
        event.setLevel(level);
        event.setGmtCreated(new Date());
        event.setUrl("");
        this.dao.insert(event);
    }
}