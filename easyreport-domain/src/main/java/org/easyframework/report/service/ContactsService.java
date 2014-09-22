package org.easyframework.report.service;

import org.easyframework.report.dao.ContactsDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.entity.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 联系人表服务类
 */
@Service
public class ContactsService extends BaseService<ContactsDao, Contacts> {
	@Autowired
	public ContactsService(ContactsDao dao) {
		super(dao);
	}
}