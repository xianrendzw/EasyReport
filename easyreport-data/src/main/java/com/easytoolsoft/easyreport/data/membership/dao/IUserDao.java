package com.easytoolsoft.easyreport.data.membership.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.membership.example.UserExample;
import com.easytoolsoft.easyreport.data.membership.po.User;
import org.springframework.stereotype.Repository;

/**
 * 系统用户(ezrpt_member_user表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMemberIUserDao")
public interface IUserDao extends ICrudDao<User, UserExample> {
}
