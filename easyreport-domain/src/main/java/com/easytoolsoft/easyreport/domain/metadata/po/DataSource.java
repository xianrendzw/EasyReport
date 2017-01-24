package com.easytoolsoft.easyreport.domain.metadata.po;

import java.io.Serializable;
import java.util.Date;

import com.easytoolsoft.easyreport.data.common.helper.AESHelper;

/**
 * 报表数据源(ezrpt_meta_datasource表)持久化类
 *
 * @author Tom Deng
 */
@SuppressWarnings("serial")
public class DataSource implements Serializable {
    /**
     * 数据源ID
     */
    private Integer id;
    /**
     * 数据源唯一ID,由调接口方传入
     */
    private String uid;
    /**
     * 数据源名称
     */
    private String name;
    /**
     * 数据源驱动类
     */
    private String driverClass;
    /**
     * 数据源连接字符串(JDBC)
     */
    private String jdbcUrl;
    /**
     * 数据源登录用户名
     */
    private String user;
    /**
     * 数据源登录密码
     */
    private String password;
    /**
     * 获取报表引擎查询器类名
     * (如:com.easytoolsoft.easyreport.engine.query.MySqlQueryer)
     *
     * @return 具体Queryer类完全名称
     */
    private String queryerClass;
    /**
     * 获取报表引擎查询器使用的数据源连接池类名
     * (如:com.easytoolsoft.easyreport.engine.dbpool.C3p0DataSourcePool)
     *
     * @return 具体DataSourcePoolWrapper类完全名称
     */
    private String poolClass;
    /**
     * 数据源配置选项(JSON格式）
     */
    private String options;
    /**
     * 说明备注
     */
    private String comment;
    /**
     * 记录创建时间
     */
    private Date gmtCreated;
    /**
     * 记录修改时间
     */
    private Date gmtModified;
    
    public void encrypt(){
    	if(getJdbcUrl()!=null && !getJdbcUrl().startsWith("AES_")){
    		this.setJdbcUrl("AES_"+AESHelper.encrypt(getJdbcUrl()));
    	}
    	if(getUser()!=null && !getUser().startsWith("AES_")){
    		this.setUser("AES_"+AESHelper.encrypt(getUser()));
    	}
    	if(getPassword()!=null && !getPassword().startsWith("AES_")){
    		this.setPassword("AES_"+AESHelper.encrypt(getPassword()));
    	}
    	System.out.println("##encrypt##jdbc"+jdbcUrl);
    	System.out.println("##encrypt##User"+user);
    	System.out.println("##encrypt##Password"+password);
    }
    
    public void decrypt(){
    	if(getJdbcUrl()!=null && getJdbcUrl().startsWith("AES_")){
    		setJdbcUrl(AESHelper.decrypt(getJdbcUrl().substring(4)));
    	}
    	if(getUser()!=null && getUser().startsWith("AES_")){
    		setUser(AESHelper.decrypt(getUser().substring(4)));
    	}
    	if(getPassword()!=null && getPassword().startsWith("AES_")){
    		setPassword(AESHelper.decrypt(getPassword().substring(4)));
    	}
    	System.out.println("##decrypt##jdbc"+jdbcUrl);
    	System.out.println("##decrypt##User"+user);
    	System.out.println("##decrypt##Password"+password);
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getQueryerClass() {
		return queryerClass;
	}

	public void setQueryerClass(String queryerClass) {
		this.queryerClass = queryerClass;
	}

	public String getPoolClass() {
		return poolClass;
	}

	public void setPoolClass(String poolClass) {
		this.poolClass = poolClass;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}