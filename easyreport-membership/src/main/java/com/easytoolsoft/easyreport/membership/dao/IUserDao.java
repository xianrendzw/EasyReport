package com.easytoolsoft.easyreport.membership.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.membership.example.UserExample;
import com.easytoolsoft.easyreport.membership.po.User;
import org.springframework.stereotype.Repository;

/**
 * 系统用户(ezrpt_member_user表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMemberIUserDao")
public interface IUserDao extends ICrudDao<User, UserExample> {
}
