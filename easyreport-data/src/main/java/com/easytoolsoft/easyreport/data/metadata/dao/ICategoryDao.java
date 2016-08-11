package com.easytoolsoft.easyreport.data.metadata.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.metadata.example.CategoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import org.springframework.stereotype.Repository;

/**
 * 报表类别(ezrpt_meta_category表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMetaICategoryDao")
public interface ICategoryDao extends ICrudDao<Category, CategoryExample> {
}
