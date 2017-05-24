package com.easytoolsoft.easyreport.meta.data;

import com.easytoolsoft.easyreport.meta.domain.Category;
import com.easytoolsoft.easyreport.meta.domain.example.CategoryExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 报表类别(category表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("CategoryRepository")
public interface CategoryRepository extends CrudRepository<Category, CategoryExample, Integer> {
    /**
     * @param oldPath
     * @param newPath
     * @return
     */
    int updatePath(@Param("oldPath") String oldPath, @Param("newPath") String newPath);
}
