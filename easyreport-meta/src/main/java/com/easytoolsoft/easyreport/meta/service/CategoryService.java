package com.easytoolsoft.easyreport.meta.service;

import java.util.List;

import com.easytoolsoft.easyreport.meta.domain.Category;
import com.easytoolsoft.easyreport.meta.domain.example.CategoryExample;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 报表分类服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface CategoryService extends CrudService<Category, CategoryExample, Integer> {
    /**
     * @param id
     * @return
     */
    List<Category> getChildren(int id);

    /**
     * @param id
     * @return
     */
    boolean hasChildren(int id);

    /**
     * @param id
     * @param pid
     * @return
     */
    boolean remove(int id, int pid);

    /**
     * @param sourceId
     * @param targetId
     * @param sourcePid
     * @param sourcePath
     */
    void move(int sourceId, int targetId, int sourcePid, String sourcePath);

    /**
     * @param sourceId
     * @param targetId
     * @return
     */
    Category paste(int sourceId, int targetId);

    /**
     * @param sourceId
     * @param targetId
     */
    void clone(int sourceId, int targetId);

    /**
     * @param id
     */
    void rebuildPathById(int id);

    /**
     *
     */
    void rebuildAllPath();
}
