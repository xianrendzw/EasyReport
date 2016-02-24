package com.easytoolsoft.easyreport.po;

import com.easytoolsoft.easyreport.common.serializer.CustomDateTimeSerializer;
import com.easytoolsoft.easyreport.data.annotations.Column;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 配置字典表持久类
 */
public class ConfigDictPo implements Serializable {
    /**
     * 列名id,配置字典ID
     */
    public final static String Id = "id";
    /**
     * 列名pid,父ID
     */
    public final static String Pid = "pid";
    /**
     * 列名name,名称
     */
    public final static String Name = "name";
    /**
     * 列名key,
     */
    public final static String Key = "`key`";
    /**
     * 列名value,
     */
    public final static String Value = "`value`";
    /**
     * 列名sequence,
     */
    public final static String Sequence = "sequence";
    /**
     * 列名comment,
     */
    public final static String Comment = "comment";
    /**
     * 列名create_time,
     */
    public final static String CreateTime = "create_time";
    /**
     * 列名update_time,
     */
    public final static String UpdateTime = "update_time";
    private static final long serialVersionUID = -8563512032577668155L;
    /**
     * 实体config_dict名称
     */
    public static String EntityName = "config_dict";
    @Column(name = "id", isIgnored = true, isPrimaryKey = true)
    private Integer id;
    @Column(name = "pid")
    private Integer pid = 0;
    @Column(name = "name")
    private String name;
    @Column(name = "`key`")
    private String key;
    @Column(name = "`value`")
    private String value;
    @Column(name = "sequence")
    private Integer sequence = 100;
    @Column(name = "comment")
    private String comment = "";
    @Column(name = "create_time")
    private Date createTime = Calendar.getInstance().getTime();
    @Column(name = "update_time")
    private Date updateTime = Calendar.getInstance().getTime();

    /**
     * 获取配置字典ID
     *
     * @return 配置字典ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置配置字典ID
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取父ID
     *
     * @return 父ID
     */
    public Integer getPid() {
        return this.pid;
    }

    /**
     * 设置父ID
     *
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getKey() {
        return this.key;
    }

    /**
     * 设置
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 设置
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * 设置
     *
     * @param value
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * 设置
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
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime == null ? Calendar.getInstance().getTime() : createTime;
    }

    /**
     * 设置
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     *
     * @return
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getUpdateTime() {
        return updateTime == null ? Calendar.getInstance().getTime() : updateTime;
    }

    /**
     * 设置
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
