package com.easytoolsoft.easyreport.membership.data;

import com.easytoolsoft.easyreport.membership.domain.Module;
import com.easytoolsoft.easyreport.membership.domain.example.ModuleExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统模块(easyreport_member_module表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("ModuleRepository")
public interface ModuleRepository extends CrudRepository<Module, ModuleExample, Integer> {
    int updatePath(@Param("oldPath") String oldPath, @Param("newPath") String newPath);
}
