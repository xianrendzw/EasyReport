package com.easytoolsoft.easyreport.metadata.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.metadata.po.Category;
import org.springframework.stereotype.Repository;

/**
 * 报表类别(ezrpt_meta_category表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMetaICategoryDao")
public interface ICategoryDao extends ICrudDao<Category> {
}
