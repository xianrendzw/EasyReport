package com.easytoolsoft.easyreport.domain.sys.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.sys.dao.IConfDao;
import com.easytoolsoft.easyreport.data.sys.example.ConfExample;
import com.easytoolsoft.easyreport.data.sys.po.Conf;
import com.easytoolsoft.easyreport.domain.sys.service.IConfService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("EzrptSysConfService")
public class ConfService
        extends AbstractCrudService<IConfDao, Conf, ConfExample>
        implements IConfService {

    @Override
    protected ConfExample getPageExample(String fieldName, String keyword) {
        ConfExample example = new ConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Conf> getByPage(PageInfo pageInfo, Integer pid) {
        ConfExample example = new ConfExample();
        example.or().andParentIdEqualTo(pid);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Conf> getByParentId(Integer parentId) {
        return this.dao.selectByParentId(parentId);
    }

    @Override
    public List<Conf> getByParentKey(String key) {
        return this.dao.selectByParentKey(key);
    }

    @Override
    public Map<String, List<Conf>> getDepth2ByParentKey(String key) {
        Map<String, List<Conf>> itemMap = new HashMap<>(10);
        List<Conf> items = this.getAll();

        Conf parentItem = items.stream()
                .filter(x -> x.getKey().equals(key))
                .findFirst()
                .get();

        List<Conf> subItems = items.stream()
                .filter(x -> Objects.equals(x.getParentId(), parentItem.getId()))
                .collect(Collectors.toList());

        for (Conf subItem : subItems) {
            itemMap.put(subItem.getKey(), items.stream()
                    .filter(x -> Objects.equals(x.getParentId(), subItem.getId()))
                    .collect(Collectors.toList()));
        }

        return itemMap;
    }

    @Override
    public List<Conf> getByPage(PageInfo page, Integer parentId, String fieldName, String keyword) {
        ConfExample example = new ConfExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andFieldLike(fieldName, keyword);
        return this.getByPage(page, example);
    }
}