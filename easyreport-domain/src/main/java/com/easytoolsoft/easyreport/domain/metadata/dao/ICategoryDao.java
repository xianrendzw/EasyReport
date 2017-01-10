package com.easytoolsoft.easyreport.domain.metadata.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.domain.metadata.example.CategoryExample;
import com.easytoolsoft.easyreport.domain.metadata.po.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 报表类别(ezrpt_meta_category表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMetaICategoryDao")
public interface ICategoryDao extends ICrudDao<Category, CategoryExample> {
    int updatePath(@Param("oldPath") String oldPath, @Param("newPath") String newPath);
}
