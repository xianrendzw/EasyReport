package com.easytoolsoft.easyreport.domain.metadata.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.domain.metadata.example.ConfExample;
import com.easytoolsoft.easyreport.domain.metadata.po.Conf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报表配置项(ezrpt_meta_conf表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMetaIConfDao")
public interface IConfDao extends ICrudDao<Conf, ConfExample> {
    List<Conf> selectByParentId(Integer parentId);

    List<Conf> selectByParentKey(@Param(value = "key") String key);
}
