package com.easytoolsoft.easyreport.meta.data;

import java.util.List;

import com.easytoolsoft.easyreport.meta.domain.Conf;
import com.easytoolsoft.easyreport.meta.domain.example.ConfExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 报表配置项(_rpt_conf表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("ConfRepository")
public interface ConfRepository extends CrudRepository<Conf, ConfExample, Integer> {
    /**
     * @param parentId
     * @return
     */
    List<Conf> selectByParentId(Integer parentId);

    /**
     * @param key
     * @return
     */
    List<Conf> selectByParentKey(@Param(value = "key") String key);
}
