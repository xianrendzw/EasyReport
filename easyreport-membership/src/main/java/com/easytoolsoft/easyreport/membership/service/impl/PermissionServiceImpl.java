package com.easytoolsoft.easyreport.membership.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.easytoolsoft.easyreport.membership.data.PermissionRepository;
import com.easytoolsoft.easyreport.membership.domain.Permission;
import com.easytoolsoft.easyreport.membership.domain.example.PermissionExample;
import com.easytoolsoft.easyreport.membership.service.PermissionService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("PermissionService")
public class PermissionServiceImpl
    extends AbstractCrudService<PermissionRepository, Permission, PermissionExample, Integer>
    implements PermissionService {

    private static final byte[] lock = new byte[0];
    private static Map<String, Permission> cache;

    public PermissionServiceImpl() {
    }

    @PostConstruct
    private void loadCache() {
        synchronized (lock) {
            if (MapUtils.isEmpty(cache)) {
                final List<Permission> list = this.dao.selectAllWithModulePath();
                cache = new HashMap<>(list.size());
                for (final Permission permission : list) {
                    cache.put(permission.getCode(), permission);
                }
            }
        }
    }

    @Override
    public void reloadCache() {
        if (cache != null) {
            cache.clear();
        }
        this.loadCache();
    }

    @Override
    public List<Permission> getByPage(final PageInfo pageInfo, final Integer moduleId) {
        final PermissionExample example = new PermissionExample();
        example.or().andModuleIdEqualTo(moduleId);
        return this.getByPage(pageInfo, example);
    }

    @Override
    protected PermissionExample getPageExample(final String fieldName, final String keyword) {
        final PermissionExample example = new PermissionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Permission> getByModuleId(final Integer moduleId) {
        final PermissionExample example = new PermissionExample();
        example.or().andModuleIdEqualTo(moduleId);
        return this.dao.selectByExample(example);
    }

    @Override
    public Map<String, String> getIdCodeMap() {
        final Map<String, String> idCodeMap = new HashMap<>(cache.size());
        for (final Entry<String, Permission> es : cache.entrySet()) {
            idCodeMap.put(es.getValue().getId().toString(), es.getValue().getCode());
        }
        return idCodeMap;
    }

    @Override
    public String getPermissionIds(final String[] codes) {
        if (ArrayUtils.isEmpty(codes)) {
            return StringUtils.EMPTY;
        }

        final List<String> permIds = new ArrayList<>(codes.length);
        for (final String code : codes) {
            final String key = code.trim().toLowerCase();
            if (cache.containsKey(key)) {
                permIds.add(String.valueOf(cache.get(key).getId()));
            }
        }

        return StringUtils.join(permIds, ',');
    }

    @Override
    public String getModuleIds(final String permissionIds) {
        // 如果设置为所有权限
        if (permissionIds.contains("all")) {
            return "all";
        }

        final Map<String, Permission> idMap = new HashMap<>(cache.size());
        for (final Permission permission : cache.values()) {
            idMap.put(permission.getId().toString(), permission);
        }

        final String[] idList = StringUtils.split(permissionIds, ',');
        final List<String> modulePathList = new ArrayList<>();
        for (final String id : idList) {
            final Permission permission = idMap.get(id);
            if (permission != null) {
                modulePathList.add(permission.getPath());
            }
        }

        final String moduleIds = StringUtils.join(modulePathList, ',');
        return this.distinct(StringUtils.split(moduleIds, ','));
    }

    private String distinct(final String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return StringUtils.EMPTY;
        }
        final Set<String> idSet = new HashSet<>(ids.length);
        Collections.addAll(idSet, ids);
        return StringUtils.join(idSet, ',');
    }
}