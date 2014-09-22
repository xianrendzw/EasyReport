package org.easyframework.report.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.easyframework.report.common.serializer.CustomDateTimeSerializer;
import org.easyframework.report.data.annotations.Column;

/**
 * 报表管理员信息表持久类
 */
public class ReportingUser implements Serializable {
	private static final long serialVersionUID = -5729509408683321327L;

	/**
	 * 实体reporting_user名称
	 */
	public static String EntityName = "reporting_user";

	/**
	 * 列名id,报表管理员ID
	 */
	public final static String Id = "id";

	/**
	 * 列名report_id,报表ID
	 */
	public final static String ReportId = "report_id";

	/**
	 * 列名user_id,用户ID
	 */
	public final static String UserId = "user_id";

	/**
	 * 列名comment,说明备注
	 */
	public final static String Comment = "comment";

	/**
	 * 列名create_time,记录创建时间
	 */
	public final static String CreateTime = "create_time";

	/**
	 * 列名update_time,记录修改时间
	 */
	public final static String UpdateTime = "update_time";

	@Column(name = "id", isIgnored = true, isPrimaryKey = true)
	private Integer id;
	@Column(name = "report_id")
	private Integer reportId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "comment")
	private String comment;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 获取报表管理员ID
	 * 
	 * @return 报表管理员ID
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置报表管理员ID
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取报表ID
	 * 
	 * @return 报表ID
	 */
	public Integer getReportId() {
		return this.reportId;
	}

	/**
	 * 设置报表ID
	 * 
	 * @param reportId
	 */
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return 用户ID
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置用户ID
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
