package com.easytoolsoft.easyreport.web.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/dict")
public class ConfController extends AbstractController {
    @Resource
    private IConfigService configService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "sys/dict";
    }

    @RequestMapping(value = "/list")

    public Map<String, Object> list(Integer id, HttpServletRequest req) {
        if (id == null) {
            id = 0;
        }

        List<Config> list = this.configService.queryByParentId(id);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/listchildren")

    public List<TreeNode<Config>> listChildren(Integer id, HttpServletRequest req) {
        if (id == null) {
            id = 0;
        }
        List<Config> list = this.configService.queryByParentId(id);
        List<TreeNode<Config>> treeNodes = new ArrayList<TreeNode<Config>>(list.size());
        for (Config po : list) {
            String configId = Integer.toString(po.getId());
            String pid = Integer.toString(po.getPid());
            String text = po.getName();
            String state = po.getHasChild() > 0 ? "closed" : "open";
            String icon = po.getHasChild() > 0 ? "icon-dict2" : "icon-item1";
            TreeNode<Config> vmMode = new TreeNode<Config>(configId, pid, text, state, icon, false, po);
            treeNodes.add(vmMode);
        }

        return treeNodes;
    }

    @RequestMapping(value = "/find")

    public Map<String, Object> find(String fieldName, String keyword, DataGridPager pager, HttpServletRequest req) {
        pager.setDefaultSort(Config.getColumnName(""));
        pager.setSort(Config.getColumnName(pager.getSort()));
        PageInfo pageInfo = new PageInfo((pager.getPage() - 1) * pager.getRows(),
                pager.getRows(), pager.getSort(), pager.getOrder());
        List<Config> list = this.configService.queryForPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);

        return modelMap;
    }

    @RequestMapping(value = "/add")

    public JsonResult add(Config po, HttpServletRequest req) {
        JsonResult result = new JsonResult();
        try {
            po.setGmtCreate(new Date());
            po.setGmtModified(new Date());
            this.configService.add(po);
            this.logSuccessResult(result, String.format("增加配置项[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("增加配置项:[%s]操作失败!", po.getName()));
            this.logExceptionResult(result, ex, req);
        }

        return result;
    }

    @RequestMapping(value = "/edit")

    public JsonResult edit(Config po, HttpServletRequest req) {
        JsonResult result = new JsonResult();

        try {
            this.configService.editSelective(po);
            this.logSuccessResult(result, String.format("修改配置项[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改配置项:[%s]操作失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }

        return result;
    }

    @RequestMapping(value = "/remove")

    public JsonResult remove(int id, HttpServletRequest req) {
        JsonResult result = new JsonResult();

        try {
            this.configService.remove(id);
            this.logSuccessResult(result, String.format("删除配置项[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            result.setMsg(String.format("删除配置项[ID:%s]操作失败!", id));
            this.logExceptionResult(result, ex, req);
        }

        return result;
    }

    @RequestMapping(value = "/copy")

    public JsonResult copy(Config po, HttpServletRequest req) {
        JsonResult result = new JsonResult();

        try {
            this.configService.add(po);
            this.logSuccessResult(result, String.format("复制系统配置项[%s]成功!", po.getK()), req);
        } catch (Exception ex) {
            this.logExceptionResult(result, ex, req);
        }

        return result;
    }

    @RequestMapping(value = "/getDepth1Items")

    public List<Config> getDepth1Items(String parentKey) {
        return this.configService.getDepth1Items(parentKey);
    }

    @RequestMapping(value = "/getDepth2Items")

    public Map<String, List<Config>> getDepth2Items(String parentKey) {
        return this.configService.getDepth2Items(parentKey);
    }

}