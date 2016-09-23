package com.easytoolsoft.easyreport.domain.sys.service;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.sys.example.ConfExample;
import com.easytoolsoft.easyreport.data.sys.po.Conf;

import java.util.List;
import java.util.Map;

/**
 * 系统配置服务类
 *
 * @author Tom Deng
 */
public interface IConfService extends ICrudService<Conf, ConfExample> {
    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<Conf> getByPage(PageInfo pageInfo, Integer pid);

    /**
     * @param parentId
     * @return
     */
    List<Conf> getByParentId(Integer parentId);

    /**
     * @param key
     * @return
     */
    List<Conf> getByParentKey(String key);

    /**
     * 获取指定parent key下2层深度的配置项
     *
     * @param key
     * @return
     */
    Map<String, List<Conf>> getDepth2ByParentKey(String key);

    /**
     * @param page
     * @param parentId
     * @param fieldName
     * @param keyword
     * @return
     */
    List<Conf> getByPage(PageInfo page, Integer parentId, String fieldName, String keyword);
}