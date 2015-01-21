package com.easytoolsoft.easyreport.data.jdbc;

import java.io.Serializable;

import com.easytoolsoft.easyreport.data.annotations.Column;

public class CategoryPo implements Serializable {
	private static final long serialVersionUID = 7746155700054643942L;
	public final static String TableName = "category";
	public final static String Id = "id";
	public final static String ParentId = "parent_id";
	public final static String Name = "name";
	public final static String ParentName = "parent_name";
	public final static String Comment = "comment";
	public final static String CreateTime = "create_time";
	public final static String UpdateTime = "update_time";

	@Column(name = "id", isPrimaryKey = true, isIdentity = true)
	private Integer id;
	@Column(name = "parent_id")
	private Integer parentId;
	@Column(name = "name")
	private String name;
	@Column(name = "parent_name")
	private String parentName;
	@Column(name = "comment")
	private String comment;
	@Column(name = "create_time")
	private String createTime;
	@Column(name = "update_time")
	private String updateTime;

	public CategoryPo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String value) {
		this.comment = value;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
