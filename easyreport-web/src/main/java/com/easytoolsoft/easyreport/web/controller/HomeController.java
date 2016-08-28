package com.easytoolsoft.easyreport.web.controller;

import com.easytoolsoft.easyreport.data.membership.po.Module;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.membership.service.MembershipFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Home页控制器
 */
@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {
    @Resource
    private MembershipFacade membershipFacade;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(@CurrentUser User loginUser, Model model, HttpServletRequest req) {
        List<Module> modules = membershipFacade.getModules(loginUser.getRoles());
        model.addAttribute("menus", this.buildMenuItems(modules, req.getContextPath()));
        model.addAttribute("roleNames", membershipFacade.getRoleNames(loginUser.getRoles()));
        model.addAttribute("user", loginUser);
        return "index";
    }

    private String buildMenuItems(List<Module> modules, String contextPath) {
        List<Module> rootModules = modules.stream()
                .filter(x -> x.getParentId() == 0 && x.getStatus() == 1)
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .collect(Collectors.toList());

        StringBuilder menuBuilder = new StringBuilder();
        menuBuilder.append("<div style=\"padding: 2px 5px;\">\r\n");
        for (Module module : rootModules) {
            String url = module.getLinkType() == 1 ? module.getUrl() : String.format("%s/%s", contextPath, module.getUrl());
            String subMenu = module.getHasChild() > 0 ? String.format("menu:'#mm%1$s'", module.getId()) : "plain:true";
            String button = module.getHasChild() > 0 ? "easyui-menubutton" : "easyui-linkbutton";
            String onclick = module.getHasChild() > 0 ? "" : String.format("onclick=\"HomeIndex.addTab('%1$s','%2$s','%3$s')\"", module.getName(), url, module.getIcon());
            menuBuilder.append(String.format("<a href=\"#\" class=\"%1$s\" " + "data-options=\"%2$s,iconCls:'%3$s'\" %4$s>%5$s</a>\r\n",
                    button, subMenu, module.getIcon(), onclick, module.getName()));
        }
        menuBuilder.append(String.format("<a href=\"%s/membership/logout\" class=\"easyui-linkbutton\"" + " data-options=\"plain:true,iconCls:'icon-cancel'\">退出</a>\r\n", contextPath));
        menuBuilder.append("</div>\r\n");

        for (Module module : rootModules) {
            this.buildMenuItems(modules, module.getId(), contextPath, menuBuilder);
        }
        return menuBuilder.toString();
    }

    private void buildMenuItems(List<Module> modules, final int parentId, String contextPath, StringBuilder menuBuilder) {
        List<Module> childModules = modules.stream()
                .filter(x -> x.getParentId() == parentId && x.getStatus() == 1)
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .collect(Collectors.toList());
        if (childModules.size() == 0) {
            return;
        }

        menuBuilder.append(String.format("<div id=\"mm%s\" style=\"width: 150px;\">\r\n", parentId));
        for (Module module : childModules) {
            String url = module.getLinkType() == 1 ? module.getUrl() : String.format("%s/%s", contextPath, module.getUrl());
            menuBuilder.append(String.format("<div data-options=\"iconCls:'%1$s'\" " + "onclick=\"HomeIndex.addTab('%2$s','%3$s','%1$s')\">%2$s</div>\r\n",
                    module.getIcon(), module.getName(), url));
            this.buildMenuItems(modules, module.getId(), contextPath, menuBuilder);
        }
        menuBuilder.append("</div>\r\n");
    }
}