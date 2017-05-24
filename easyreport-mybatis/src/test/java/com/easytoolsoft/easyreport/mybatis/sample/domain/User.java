package com.easytoolsoft.easyreport.mybatis.sample.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 持久化类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements Serializable {
    /**
     * 系统用户标识
     */
    private Integer id;
    /**
     * 系统用户所属角色集合(role_id以英文逗号分隔)
     */
    @NotEmpty(message = "{member.user.roles.NotEmpty}")
    @Length(max = 20, message = "{member.user.roles.Length}")
    private String roles;
    /**
     * 系统用户账号
     */
    @NotEmpty(message = "{member.user.account.NotEmpty}")
    @Length(min = 8, max = 20, message = "{member.user.account.Length}")
    private String account;
    /**
     * 系统用户密码
     */
    @NotEmpty
    @Length(max = 64)
    private String password;
    /**
     * 加盐
     */
    private String salt;
    /**
     * 系统用户姓名
     */
    @NotEmpty
    @Length(max = 20)
    private String name;
    /**
     * 系统用户电子邮箱
     */
    @Email
    private String email;
    /**
     * 系统用户用户电话号码,多个用英文逗号分开
     */
    @Length(max = 13)
    private String telephone;
    /**
     * 系统用户的状态,1表示启用,0表示禁用,默认为1,其他保留
     */
    @NotNull
    private Byte status;
    /**
     * 系统用户备注
     */
    @NotNull
    private String comment;
    /**
     * 系统用户记录创建时间
     */
    private Date gmtCreated;
    /**
     * 系统用户记录更新时间戳
     */
    private Date gmtModified;

}
