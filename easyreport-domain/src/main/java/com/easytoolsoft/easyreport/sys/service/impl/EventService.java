package com.easytoolsoft.easyreport.sys.service.impl;

import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.sys.dao.IEventDao;
import com.easytoolsoft.easyreport.sys.po.Event;
import com.easytoolsoft.easyreport.sys.service.IEventService;
import org.springframework.stereotype.Service;

@Service("EzrptSysEventService")
public class EventService extends AbstractCrudService<IEventDao, Event> implements IEventService {
}