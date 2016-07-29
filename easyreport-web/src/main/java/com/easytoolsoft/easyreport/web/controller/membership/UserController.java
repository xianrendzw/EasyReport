package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.security.PasswordService;
import com.easytoolsoft.easyreport.membership.service.IUserService;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/membership/user")
public class UserController extends AbstractController {
    @Resource
    private IUserService userService;
    @Resource
    private PasswordService passwordService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "membership/user";
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(@CurrentUser User loginUser, DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<User> list = this.userService.getByPage(pageInfo);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/find")
    public Map<String, Object> find(@CurrentUser User loginUser,
                                    String fieldName, String keyword, DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<User> list = this.userService.getByPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(User po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            po.setGmtCreated(new Date());
            po.setGmtModified(new Date());
            this.userService.encryptPassword(po);
            this.userService.add(po);
            this.logSuccessResult(result, String.format("增加用户[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("增加用户:[%s]操作失败!", po.getName()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(User po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.userService.editById(po);
            this.logSuccessResult(result, String.format("修改用户[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改用户:[%s]操作失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.userService.removeById(id);
            this.logSuccessResult(result, String.format("删除用户[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            result.setMsg(String.format("删除用户[ID:%s]操作失败!", id));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/editPassword")
    public JsonResult editPassword(Integer userId, String password, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            User user = this.userService.getById(userId);
            user.setPassword(password);
            this.userService.encryptPassword(user);
            this.userService.editById(user);
            this.logSuccessResult(result, String.format("更新用户[ID:%s]密码成功!", userId), req);
        } catch (Exception ex) {
            result.setMsg(String.format("更新用户[ID:%s]密码失败!", userId));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/changeMyPassword")
    public JsonResult changeMyPassword(@CurrentUser User loginUser, String password, String oldPassword,
                                       HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            String encryptPassword = this.passwordService.encryptPassword(oldPassword,
                    loginUser.getCredentialsSalt());
            if (!encryptPassword.equals(loginUser.getPassword())) {
                this.logFailureResult(result, "原账户密码错误！", req);
                return result;
            }
            loginUser.setPassword(password);
            this.userService.encryptPassword(loginUser);
            this.userService.editById(loginUser);
            this.logSuccessResult(result, String.format("修改用户[ID:%s]密码成功!", loginUser.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改用户[ID:%s]密码失败!", loginUser.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }
}
