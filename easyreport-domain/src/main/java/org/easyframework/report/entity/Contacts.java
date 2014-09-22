package org.easyframework.report.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.easyframework.report.common.serializer.CustomDateTimeSerializer;
import org.easyframework.report.data.annotations.Column;

/**
 * 联系人表持久类
 */
public class Contacts implements Serializable {
	private static final long serialVersionUID = 8998128866686710275L;

	/**
	 * 实体contacts名称
	 */
	public static String EntityName = "contacts";

	/**
	 * 列名id,联系人id
	 */
	public final static String Id = "id";

	/**
	 * 列名pid,联系人父id
	 */
	public final static String Pid = "pid";

	/**
	 * 列名name,联系人姓名
	 */
	public final static String Name = "name";

	/**
	 * 列名email,联系人电子邮箱
	 */
	public final static String Email = "email";

	/**
	 * 列名telphone,联系人电话
	 */
	public final static String Telphone = "telphone";

	/**
	 * 列名create_time,联系人记录创建时间
	 */
	public final static String CreateTime = "create_time";

	/**
	 * 列名update_time,联系人记录更新时间
	 */
	public final static String UpdateTime = "update_time";

	@Column(name = "id", isIgnored = true, isPrimaryKey = true)
	private Integer id;
	@Column(name = "pid")
	private Integer pid;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "telphone")
	private String telphone;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 获取联系人id
	 * 
	 * @return 联系人id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置联系人id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取联系人父id
	 * 
	 * @return 联系人父id
	 */
	public Integer getPid() {
		return this.pid;
	}

	/**
	 * 设置联系人父id
	 * 
	 * @param pid
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 获取联系人姓名
	 * 
	 * @return 联系人姓名
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置联系人姓名
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取联系人电子邮箱
	 * 
	 * @return 联系人电子邮箱
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * 设置联系人电子邮箱
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取联系人电话
	 * 
	 * @return 联系人电话
	 */
	public String getTelphone() {
		return this.telphone;
	}

	/**
	 * 设置联系人电话
	 * 
	 * @param telphone
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	/**
	 * 获取联系人记录创建时间
	 * 
	 * @return 联系人记录创建时间
	 */
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime == null ? Calendar.getInstance().getTime() : createTime;
	}

	/**
	 * 设置联系人记录创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取联系人记录更新时间
	 * 
	 * @return 联系人记录更新时间
	 */
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime == null ? Calendar.getInstance().getTime() : updateTime;
	}

	/**
	 * 设置联系人记录更新时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
