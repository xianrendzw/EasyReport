package com.easytoolsoft.easyreport.web.controller.home;

import java.util.List;

import javax.annotation.Resource;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.membership.domain.Module;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.service.impl.MembershipFacadeServiceImpl;
import com.easytoolsoft.easyreport.support.annotation.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Home页控制器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {
    @Resource
    private MembershipFacadeServiceImpl membershipFacade;

    @GetMapping(value = {"", "/", "/index"})
    public String index(@CurrentUser final User loginUser, final Model model) {
        model.addAttribute("roleNames", this.membershipFacade.getRoleNames(loginUser.getRoles()));
        model.addAttribute("user", loginUser);
        return "home/index";
    }

    @ResponseBody
    @GetMapping(value = "/rest/home/getMenus")
    public List<EasyUITreeNode<Module>> getMenus(@CurrentUser final User loginUser) {
        final List<Module> modules = this.membershipFacade.getModules(loginUser.getRoles());
        return this.membershipFacade.getModuleTree(modules, x -> x.getStatus() == 1);
    }
}