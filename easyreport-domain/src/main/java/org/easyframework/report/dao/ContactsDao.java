package org.easyframework.report.dao;

import org.easyframework.report.data.jdbc.BaseDao;
import org.easyframework.report.entity.Contacts;
import org.springframework.stereotype.Repository;

/**
 * ContactsDao提供联系人表表(contacts)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ContactsDao extends BaseDao<Contacts> {

	public ContactsDao() {
		super(Contacts.EntityName, Contacts.Id);
	}
}