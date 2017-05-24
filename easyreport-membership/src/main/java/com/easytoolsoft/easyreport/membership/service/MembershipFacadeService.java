package com.easytoolsoft.easyreport.membership.service;

import java.util.List;
import java.util.function.Predicate;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.membership.domain.Module;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.support.security.MembershipFacade;

/**
 * 用户权限服务外观类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface MembershipFacadeService extends MembershipFacade<User> {
    void loadCache();

    List<EasyUITreeNode<Module>> getModuleTree(final List<Module> modules, final Predicate<Module> predicate);

    List<Module> getModules(final String roleIds);
}
