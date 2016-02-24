package com.easytoolsoft.easyreport.po;

import com.easytoolsoft.easyreport.common.serializer.CustomDateTimeSerializer;
import com.easytoolsoft.easyreport.data.annotations.Column;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 数据源配置信息表持久类
 */
public class DataSourcePo implements Serializable {
    /**
     * 列名id,数据源ID
     */
    public final static String Id = "id";
    /**
     * 列名uid,数据源唯一ID,由调接口方传入
     */
    public final static String Uid = "uid";
    /**
     * 列名name,数据源名称
     */
    public final static String Name = "name";
    /**
     * 列名jdbc_url,数据源连接字符串(JDBC)
     */
    public final static String JdbcUrl = "jdbc_url";
    /**
     * 列名user,数据源登录用户名
     */
    public final static String User = "user";
    /**
     * 列名password,数据源登录密码
     */
    public final static String Password = "password";
    /**
     * 列名comment,说明备注
     */
    public final static String Comment = "comment";
    /**
     * 列名create_user,
     */
    public final static String CreateUser = "create_user";
    /**
     * 列名create_time,记录创建时间
     */
    public final static String CreateTime = "create_time";
    /**
     * 列名update_time,记录修改时间
     */
    public final static String UpdateTime = "update_time";
    private static final long serialVersionUID = 8536431452814609439L;
    /**
     * 实体datasource名称
     */
    public static String EntityName = "datasource";
    @Column(name = "id", isIgnored = true, isPrimaryKey = true)
    private Integer id;
    @Column(name = "uid")
    private String uid;
    @Column(name = "name")
    private String name;
    @Column(name = "jdbc_url")
    private String jdbcUrl;
    @Column(name = "user")
    private String user;
    @Column(name = "password")
    private String password;
    @Column(name = "comment")
    private String comment = "";
    @Column(name = "create_user")
    private String createUser = "";
    @Column(name = "create_time")
    private Date createTime = Calendar.getInstance().getTime();
    @Column(name = "update_time")
    private Date updateTime = Calendar.getInstance().getTime();

    /**
     * 获取数据源ID
     *
     * @return 数据源ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置数据源ID
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取数据源唯一ID,由调接口方传入
     *
     * @return 数据源唯一ID, 由调接口方传入
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * 设置数据源唯一ID,由调接口方传入
     *
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 获取数据源名称
     *
     * @return 数据源名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置数据源名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取数据源连接字符串(JDBC)
     *
     * @return 数据源连接字符串(JDBC)
     */
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    /**
     * 设置数据源连接字符串(JDBC)
     *
     * @param jdbcUrl
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * 获取数据源登录用户名
     *
     * @return 数据源登录用户名
     */
    public String getUser() {
        return this.user;
    }

    /**
     * 设置数据源登录用户名
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取数据源登录密码
     *
     * @return 数据源登录密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置数据源登录密码
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取说明备注
     *
     * @return 说明备注
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * 设置说明备注
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置
     *
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取记录创建时间
     *
     * @return 记录创建时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime == null ? Calendar.getInstance().getTime() : createTime;
    }

    /**
     * 设置记录创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取记录修改时间
     *
     * @return 记录修改时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getUpdateTime() {
        return updateTime == null ? Calendar.getInstance().getTime() : updateTime;
    }

    /**
     * 设置记录修改时间
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
