package com.easytoolsoft.easyreport.data.membership.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.membership.example.ModuleExample;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统模块(ezrpt_member_module表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMemberIModuleDao")
public interface IModuleDao extends ICrudDao<Module, ModuleExample> {
    int updatePath(@Param("oldPath") String oldPath, @Param("newPath") String newPath);
}
