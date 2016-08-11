package com.easytoolsoft.easyreport.metadata.service;

import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.metadata.example.CategoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.Category;

import java.util.List;

/**
 * 报表分类服务类
 *
 * @author Tom Deng
 */
public interface ICategoryService extends ICrudService<Category, CategoryExample> {

    List<Category> getChildren(int id);

    boolean hasChildren(int id);

    boolean remove(int id, int pid);

    void move(int sourceId, int targetId, int sourcePid);

    Category paste(int sourceId, int targetId);

    void clone(int sourceId, int targetId);

    void rebuildPathById(int id);

    void rebuildAllPath();
}
