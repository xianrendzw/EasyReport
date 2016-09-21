package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.membership.example.UserExample;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.membership.security.PasswordService;
import com.easytoolsoft.easyreport.membership.service.IUserService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取用户列表")
    @RequiresPermissions("membership.user:view")
    public Map<String, Object> list(@CurrentUser User loginUser, DataGridPager pager,
                                    String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<User> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增用户")
    @RequiresPermissions("membership.user:add")
    public JsonResult add(User po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.encryptPassword(po);
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改用户")
    @RequiresPermissions("membership.user:edit")
    public JsonResult edit(User po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.encryptPassword(po);
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除用户")
    @RequiresPermissions("membership.user:remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @RequestMapping(value = "/editPassword")
    @OpLog(name = "修改用户登录密码")
    @RequiresPermissions("membership.user:editPassword")
    public JsonResult editPassword(Integer userId, String password) {
        JsonResult<String> result = new JsonResult<>();
        User user = this.service.getById(userId);
        user.setPassword(password);
        this.service.encryptPassword(user);
        this.service.editById(user);
        return result;
    }

    @RequestMapping(value = "/changeMyPassword")
    @OpLog(name = "修改个人登录密码")
    @RequiresPermissions("membership.user:changeMyPassword")
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
