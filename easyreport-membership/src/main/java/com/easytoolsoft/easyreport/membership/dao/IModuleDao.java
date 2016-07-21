package com.easytoolsoft.easyreport.membership.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.membership.po.Module;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统模块(ezrpt_member_module表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMemberIModuleDao")
public interface IModuleDao extends ICrudDao<Module> {
}
