package com.easytoolsoft.easyreport.sys.service.impl;

import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.sys.dao.IEventDao;
import com.easytoolsoft.easyreport.data.sys.po.Event;
import com.easytoolsoft.easyreport.sys.service.IEventService;
import org.springframework.stereotype.Service;

@Service("EzrptSysEventService")
public class EventService extends AbstractCrudService<IEventDao, Event> implements IEventService {
    @Override
    public void clear() {
        this.dao.delete(null);
    }
}