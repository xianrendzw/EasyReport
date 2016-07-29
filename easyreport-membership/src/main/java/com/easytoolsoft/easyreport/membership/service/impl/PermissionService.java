package com.easytoolsoft.easyreport.membership.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.ParameterBuilder;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.membership.dao.IPermissionDao;
import com.easytoolsoft.easyreport.data.membership.po.Permission;
import com.easytoolsoft.easyreport.membership.service.IPermissionService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Service("EzrptMemberPermissionService")
public class PermissionService extends AbstractCrudService<IPermissionDao, Permission> implements IPermissionService {
    private static Map<String, Permission> cache;
    private static final byte[] lock = new byte[0];

    @Autowired
    public PermissionService() {
        this.loadCache();
    }

    @Override
    public void reloadCache() {
        if (cache != null) {
            cache.clear();
        }
        this.loadCache();
    }

    private void loadCache() {
        synchronized (lock) {
            if (MapUtils.isEmpty(cache)) {
                List<Permission> list = this.dao.selectAllWithModulePath();
                cache = new HashMap<>(list.size());
                for (Permission permission : list) {
                    cache.put(permission.getCode(), permission);
                }
            }
        }
    }

    @Override
    public List<Permission> getByModuleId(Integer moduleId) {
        Map<String, Object> params = ParameterBuilder.getQueryParams(
                Permission.builder().moduleId(moduleId).build());
        return this.dao.select(params);
    }

    @Override
    public Map<String, String> getIdCodeMap() {
        Map<String, String> idCodeMap = new HashMap<>(cache.size());
        for (Entry<String, Permission> es : cache.entrySet()) {
            idCodeMap.put(es.getValue().getId().toString(), es.getValue().getCode());
        }
        return idCodeMap;
    }

    @Override
    public String getPermissionIds(String[] codes) {
        if (ArrayUtils.isEmpty(codes)) {
            return StringUtils.EMPTY;
        }

        List<String> permIds = new ArrayList<>(codes.length);
        for (String code : codes) {
            String key = code.trim().toLowerCase();
            if (cache.containsKey(key)) {
                permIds.add(String.valueOf(cache.get(key).getId()));
            }
        }

        return StringUtils.join(permIds, ',');
    }

    @Override
    public String getModuleIds(String permissionIds) {
        // 如果设置为所有权限
        if (permissionIds.contains("all")) {
            return "all";
        }

        Map<String, Permission> idMap = new HashMap<>(cache.size());
        for (Permission permission : cache.values()) {
            idMap.put(permission.getId().toString(), permission);
        }

        String[] idList = StringUtils.split(permissionIds, ',');
        List<String> modulePathList = new ArrayList<>();
        for (String id : idList) {
            Permission permission = idMap.get(id);
            if (permission != null) {
                modulePathList.add(permission.getPath());
            }
        }

        String moduleIds = StringUtils.join(modulePathList, ',');
        return this.distinct(StringUtils.split(moduleIds, ','));
    }

    private String distinct(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return StringUtils.EMPTY;
        }
        Set<String> idSet = new HashSet<>(ids.length);
        Collections.addAll(idSet, ids);
        return StringUtils.join(idSet, ',');
    }
}