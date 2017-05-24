package com.easytoolsoft.easyreport.mybatis.service;

import java.util.Date;

import javax.annotation.Resource;

import com.easytoolsoft.easyreport.mybatis.BaseTest;
import com.easytoolsoft.easyreport.mybatis.sample.domain.User;
import com.easytoolsoft.easyreport.mybatis.sample.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;

/**
 * 非分表实现测试用例
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public class UserServiceTest extends BaseTest {

    @Resource(name = "UserService")
    private UserService userService;

    @Test
    public void addTest() {
        final User user = User.builder()
            .account("easytoolsoft_test")
            .name("easytoolsoft.com")
            .password("easytoolsoft_test")
            .email("test@easytoolsoft.com")
            .roles("1")
            .salt("abcdefg")
            .status((byte)0)
            .telephone("13800000000")
            .gmtCreated(new Date())
            .comment("")
            .build();
        this.userService.add(user);
        Assert.assertThat(user.getId(), new GreaterThan<>(0));
    }
}