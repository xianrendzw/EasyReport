package org.easyframework.report.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.easyframework.report.common.serializer.CustomDateTimeSerializer;
import org.easyframework.report.data.annotations.Column;

/**
 * 报表与联系人关系表持久类
 */
public class ReportingContacts implements Serializable {
	private static final long serialVersionUID = 1787318736993729946L;

	/**
	 * 实体reporting_contacts名称
	 */
	public static String EntityName = "reporting_contacts";

	/**
	 * 列名id,报表任务邮件地址id
	 */
	public final static String Id = "id";

	/**
	 * 列名report_id,报表id
	 */
	public final static String ReportId = "report_id";

	/**
	 * 列名contacts_id,联系人id
	 */
	public final static String ContactsId = "contacts_id";

	/**
	 * 列名create_time,报表邮件地址记录创建时间
	 */
	public final static String CreateTime = "create_time";

	/**
	 * 列名update_time,报表邮件地址记录更新时间
	 */
	public final static String UpdateTime = "update_time";

	@Column(name = "id", isIgnored = true, isPrimaryKey = true)
	private Integer id;
	@Column(name = "report_id")
	private Integer reportId;
	@Column(name = "contacts_id")
	private String contactsId;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 获取报表任务邮件地址id
	 * 
	 * @return 报表任务邮件地址id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置报表任务邮件地址id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取报表id
	 * 
	 * @return 报表id
	 */
	public Integer getReportId() {
		return this.reportId;
	}

	/**
	 * 设置报表id
	 * 
	 * @param reportId
	 */
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	/**
	 * 获取联系人id
	 * 
	 * @return 联系人id
	 */
	public String getContactsId() {
		return this.contactsId;
	}

	/**
	 * 设置联系人id
	 * 
	 * @param contactsId
	 */
	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	/**
	 * 获取报表邮件地址记录创建时间
	 * 
	 * @return 报表邮件地址记录创建时间
	 */
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime == null ? Calendar.getInstance().getTime() : createTime;
	}

	/**
	 * 设置报表邮件地址记录创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取报表邮件地址记录更新时间
	 * 
	 * @return 报表邮件地址记录更新时间
	 */
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime == null ? Calendar.getInstance().getTime() : updateTime;
	}

	/**
	 * 设置报表邮件地址记录更新时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
