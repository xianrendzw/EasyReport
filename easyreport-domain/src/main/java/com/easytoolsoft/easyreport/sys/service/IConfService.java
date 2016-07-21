package com.easytoolsoft.easyreport.sys.service;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.sys.po.Conf;

import java.util.List;

/**
 * 系统配置服务类
 *
 * @author Tom Deng
 */
public interface IConfService extends ICrudService<Conf> {
    /**
     * @param parentId
     * @return
     */
    List<Conf> getByParentId(Integer parentId);

    /**
     * @param page
     * @param parentId
     * @param fieldName
     * @param keyword
     * @return
     */
    List<Conf> getByPage(PageInfo page, Integer parentId, String fieldName, String keyword);
}