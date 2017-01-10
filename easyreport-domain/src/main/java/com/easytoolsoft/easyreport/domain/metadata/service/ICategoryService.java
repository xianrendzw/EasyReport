package com.easytoolsoft.easyreport.domain.metadata.service;

import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.domain.metadata.example.CategoryExample;
import com.easytoolsoft.easyreport.domain.metadata.po.Category;

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

    void move(int sourceId, int targetId, int sourcePid, String sourcePath);

    Category paste(int sourceId, int targetId);

    void clone(int sourceId, int targetId);

    void rebuildPathById(int id);

    void rebuildAllPath();
}
