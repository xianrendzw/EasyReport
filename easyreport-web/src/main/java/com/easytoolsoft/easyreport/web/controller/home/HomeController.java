package com.easytoolsoft.easyreport.web.controller.home;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.membership.service.MembershipFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Home页控制器
 */
@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {
    @Resource
    private MembershipFacade membershipFacade;

    @GetMapping(value = {"", "/", "/index"})
    public String index(@CurrentUser User loginUser, Model model) {
        model.addAttribute("roleNames", this.membershipFacade.getRoleNames(loginUser.getRoles()));
        model.addAttribute("user", loginUser);
        return "home/index";
    }

    @ResponseBody
    @GetMapping(value = "/rest/home/getMenus")
    public List<EasyUITreeNode<Module>> getMenus(@CurrentUser User loginUser) {
        List<Module> modules = this.membershipFacade.getModules(loginUser.getRoles());
        return this.membershipFacade.getModuleTree(modules, x -> x.getStatus() == 1);
    }
}