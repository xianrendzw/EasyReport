package com.easytoolsoft.easyreport.web.controller.sys;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.sys.example.ConfExample;
import com.easytoolsoft.easyreport.data.sys.po.Conf;
import com.easytoolsoft.easyreport.sys.service.IConfService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("SysConfController")
@RequestMapping(value = "/rest/sys/dict")
public class ConfController
        extends BaseController<IConfService, Conf, ConfExample> {

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "sys/dict";
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(Integer id) {
        List<Conf> list = this.service.getByParentId(id == null ? 0 : id);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/listChildren")
    public List<EasyUITreeNode<Conf>> listChildren(Integer id) {
        List<Conf> list = this.service.getByParentId(id == null ? 0 : id);
        List<EasyUITreeNode<Conf>> EasyUITreeNodes = new ArrayList<>(list.size());
        for (Conf po : list) {
            String ConfId = Integer.toString(po.getId());
            String pid = Integer.toString(po.getParentId());
            String text = po.getName();
            String state = po.isHasChild() ? "closed" : "open";
            String icon = po.isHasChild() ? "icon-dict2" : "icon-item1";
            EasyUITreeNodes.add(new EasyUITreeNode<>(ConfId, pid, text, state, icon, false, po));
        }
        return EasyUITreeNodes;
    }

    @RequestMapping(value = "/find")
    public Map<String, Object> find(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Conf> list = this.service.getByPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(Conf po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(Conf po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @RequestMapping(value = "/copy")
    public JsonResult copy(Conf po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/getDepth1Items")
    public List<Conf> getDepth1Items(String parentKey) {
        return new ArrayList<>(0);
        // return this.service.getDepth1Items(parentKey);
    }

    @RequestMapping(value = "/getDepth2Items")
    public Map<String, List<Conf>> getDepth2Items(String parentKey) {
        return new HashMap<>(0);
        //return this.service.getDepth2Items(parentKey);
    }
}