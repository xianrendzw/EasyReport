package com.easytoolsoft.easyreport.sys.service;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.sys.example.ConfExample;
import com.easytoolsoft.easyreport.data.sys.po.Conf;

import java.util.List;

/**
 * 系统配置服务类
 *
 * @author Tom Deng
 */
public interface IConfService extends ICrudService<Conf, ConfExample> {
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