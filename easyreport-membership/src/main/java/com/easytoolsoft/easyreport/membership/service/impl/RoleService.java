package com.easytoolsoft.easyreport.membership.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.membership.dao.IRoleDao;
import com.easytoolsoft.easyreport.data.membership.example.RoleExample;
import com.easytoolsoft.easyreport.data.membership.po.Role;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.service.IRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("EzrptMemberRoleService")
public class RoleService
        extends AbstractCrudService<IRoleDao, Role, RoleExample>
        implements IRoleService {

    @Override
    protected RoleExample getPageExample(String fieldName, String keyword) {
        RoleExample example = new RoleExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Role> getByPage(PageInfo page, User currentUser, String fieldName, String keyword) {
        if (this.isSuperAdminRole(currentUser.getRoles())) {
            return this.getByPage(page, fieldName, keyword);
        }
        RoleExample example = new RoleExample();
        example.or()
                .andCreateUserEqualTo(currentUser.getAccount())
                .andFieldLike(fieldName, keyword);
        return this.getByPage(page, example);
    }

    @Override
    public boolean isSuperAdminRole(String roleIds) {
        List<Role> list = this.getIn(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return list.stream().anyMatch(x -> x.getCode().equals("superAdmin"));
    }

    @Override
    public String getNames(String roleIds) {
        List<Role> list = this.getIn(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        List<String> namesList = new ArrayList<>(list.size());
        namesList.addAll(list.stream().map(Role::getName).collect(Collectors.toList()));
        return StringUtils.join(namesList, ',');
    }

    @Override
    public String getModuleIds(String roleIds) {
        return this.getIds(roleIds, Role::getModules);
    }

    @Override
    public String getPermissionIds(String roleIds) {
        return this.getIds(roleIds, Role::getPermissions);
    }

    @Override
    public String getRoleIdsBy(String createUser) {
        List<Role> list = this.getByCreateUser(createUser);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        List<Integer> roleIdList = new ArrayList<>(list.size());
        roleIdList.addAll(list.stream().map(Role::getId).collect(Collectors.toList()));
        return StringUtils.join(roleIdList, ',');
    }

    @Override
    public List<Role> getRolesList(User logingUser) {
        if (this.isSuperAdminRole(logingUser.getRoles())) {
            return this.getAll();
        }
        return this.getByCreateUser(logingUser.getAccount());
    }

    @Override
    public Map<String, String[]> getRoleModulesAndPermissions(Integer roleId) {
        Role role = this.dao.selectById(roleId);
        if (role == null) {
            return null;
        }

        Map<String, String[]> map = new HashMap<>(2);
        String distinctModules = this.distinct(StringUtils.split(role.getModules(), ','));
        String distinctPerms = this.distinct(StringUtils.split(role.getPermissions(), ','));
        map.put("modules", StringUtils.split(distinctModules, ','));
        map.put("permissions", StringUtils.split(distinctPerms, ','));
        return map;
    }

    private String getIds(String roleIds, Function<? super Role, ? extends String> mapper) {
        List<Role> list = this.getIn(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        List<String> idList = new ArrayList<>(list.size());
        idList.addAll(list.stream().map(mapper).collect(Collectors.toList()));
        String joinIds = StringUtils.join(idList, ',');
        String[] ids = StringUtils.split(joinIds, ',');
        return this.distinct(ids);
    }

    private String distinct(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return StringUtils.EMPTY;
        }
        Set<String> idSet = new HashSet<>(ids.length);
        Collections.addAll(idSet, ids);
        return StringUtils.join(idSet, ',');
    }

    private List<Role> getIn(String roleIds) {
        if (StringUtils.isBlank(roleIds)) {
            return new ArrayList<>(0);
        }
        String[] ids = StringUtils.split(roleIds, ',');
        List<Role> list = new ArrayList<>(10);
        for (String id : ids) {
            list.add(Role.builder().id(Integer.valueOf(id)).build());
        }
        return this.dao.selectIn(list);
    }

    private List<Role> getByCreateUser(String createUser) {
        RoleExample example = new RoleExample();
        example.or().andCreateUserEqualTo(createUser);
        return this.dao.selectByExample(example);
    }
}