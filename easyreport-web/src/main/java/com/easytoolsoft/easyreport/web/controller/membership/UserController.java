package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.membership.example.UserExample;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.membership.security.PasswordService;
import com.easytoolsoft.easyreport.membership.service.IUserService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/membership/user")
public class UserController
        extends BaseController<IUserService, User, UserExample> {

    @Resource
    private PasswordService passwordService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "membership/user";
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(@CurrentUser User loginUser, DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<User> list = this.service.getByPage(pageInfo);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/find")
    public Map<String, Object> find(@CurrentUser User loginUser,
                                    String fieldName, String keyword, DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<User> list = this.service.getByPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(User po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.encryptPassword(po);
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(User po) {
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

    @RequestMapping(value = "/editPassword")
    public JsonResult editPassword(Integer userId, String password) {
        JsonResult<String> result = new JsonResult<>();
        User user = this.service.getById(userId);
        user.setPassword(password);
        this.service.encryptPassword(user);
        this.service.editById(user);
        return result;
    }

    @RequestMapping(value = "/changeMyPassword")
    public JsonResult changeMyPassword(@CurrentUser User loginUser, String password, String oldPassword) {
        JsonResult<String> result = new JsonResult<>();
        String encryptPassword = this.passwordService.encryptPassword(oldPassword, loginUser.getCredentialsSalt());
        if (!encryptPassword.equals(loginUser.getPassword())) {
            result.setSuccess(false);
            result.setMsg("原账户密码错误！");
            return result;
        }
        loginUser.setPassword(password);
        this.service.encryptPassword(loginUser);
        this.service.editById(loginUser);
        return result;
    }
}
