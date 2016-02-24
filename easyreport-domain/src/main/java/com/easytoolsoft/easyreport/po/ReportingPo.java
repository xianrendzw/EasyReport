package com.easytoolsoft.easyreport.po;

import com.alibaba.fastjson.JSON;
import com.easytoolsoft.easyreport.common.serializer.CustomDateTimeSerializer;
import com.easytoolsoft.easyreport.data.annotations.Column;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 报表信息表持久类
 */
public class ReportingPo implements Serializable {
    /**
     * 列名id,报表ID
     */
    public final static String Id = "id";
    /**
     * 列名pid,
     */
    public final static String Pid = "pid";
    /**
     * 列名ds_id,数据源ID
     */
    public final static String DsId = "ds_id";
    /**
     * 列名uid,报表唯一ID,由接口调用方传入
     */
    public final static String Uid = "uid";
    /**
     * 列名name,报表名称
     */
    public final static String Name = "name";
    /**
     * 列名path,报表树型结构路径
     */
    public final static String Path = "path";
    /**
     * 列名flag,报表树型节点标志,0为分类节点，1为报表结点
     */
    public final static String Flag = "flag";
    /**
     * 列名has_child,
     */
    public final static String HasChild = "has_child";
    /**
     * 列名status,报表状态（1表示锁定，0表示编辑)
     */
    public final static String Status = "status";
    /**
     * 列名sequence,报表节点在其父节点中的顺序
     */
    public final static String Sequence = "sequence";
    /**
     * 列名data_range,报表默认展示多少天的数据
     */
    public final static String DataRange = "data_range";
    /**
     * 列名layout,布局形式.1横向;2纵向
     */
    public final static String Layout = "layout";
    /**
     * 列名stat_column_layout,统计列布局形式.1横向;2纵向
     */
    public final static String StatColumnLayout = "stat_column_layout";
    /**
     * 列名sql_text,报表SQL语句
     */
    public final static String SqlText = "sql_text";
    /**
     * 列名meta_columns,报表元数据列集合的JSON序列化字符串
     */
    public final static String MetaColumns = "meta_columns";
    /**
     * 列名query_params,查询条件列属性集合json字符串
     */
    public final static String QueryParams = "query_params";
    /**
     * 列名comment,说明备注
     */
    public final static String Comment = "comment";
    /**
     * 列名create_user,创建用户
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
    private static final long serialVersionUID = -6362845369763426150L;
    /**
     * 实体reporting名称
     */
    public static String EntityName = "reporting";
    @Column(name = "id", isIgnored = true, isPrimaryKey = true)
    private Integer id;
    @Column(name = "pid")
    private Integer pid = 0;
    @Column(name = "ds_id")
    private Integer dsId = 0;
    @Column(name = "uid")
    private String uid;
    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path = "";
    @Column(name = "flag")
    private Integer flag = 0;
    @Column(name = "has_child")
    private boolean hasChild;
    @Column(name = "status")
    private Integer status = 0;
    @Column(name = "sequence")
    private Integer sequence = 10;
    @Column(name = "data_range")
    private Integer dataRange = 7;
    @Column(name = "layout")
    private Integer layout = 1;
    @Column(name = "stat_column_layout")
    private Integer statColumnLayout = 1;
    @Column(name = "sql_text")
    private String sqlText = "";
    @Column(name = "meta_columns")
    private String metaColumns = "";
    @Column(name = "query_params")
    private String queryParams = "";
    @Column(name = "comment")
    private String comment = "";
    @Column(name = "create_user")
    private String createUser = "";
    @Column(name = "create_time")
    private Date createTime = Calendar.getInstance().getTime();
    @Column(name = "update_time")
    private Date updateTime = Calendar.getInstance().getTime();

    /**
     * 获取报表ID
     *
     * @return 报表ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置报表ID
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getPid() {
        return this.pid;
    }

    /**
     * 设置
     *
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取数据源ID
     *
     * @return 数据源ID
     */
    public Integer getDsId() {
        return this.dsId;
    }

    /**
     * 设置数据源ID
     *
     * @param dsId
     */
    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    /**
     * 获取报表唯一ID,由接口调用方传入
     *
     * @return 报表唯一ID, 由接口调用方传入
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * 设置报表唯一ID,由接口调用方传入
     *
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 获取报表名称
     *
     * @return 报表名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置报表名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取报表树型结构路径
     *
     * @return 报表树型结构路径
     */
    public String getPath() {
        return this.path;
    }

    /**
     * 设置报表树型结构路径
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取报表树型节点标志,0为分类节点，1为报表结点
     *
     * @return 报表树型节点标志, 0为分类节点，1为报表结点
     */
    public Integer getFlag() {
        return this.flag;
    }

    /**
     * 设置报表树型节点标志,0为分类节点，1为报表结点
     *
     * @param flag
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 获取
     *
     * @return
     */
    public boolean getHasChild() {
        return this.hasChild;
    }

    /**
     * 设置
     *
     * @param hasChild
     */
    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    /**
     * 获取报表状态（1表示锁定，0表示编辑)
     *
     * @return 报表状态（1表示锁定，0表示编辑)
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置报表状态（1表示锁定，0表示编辑)
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取报表节点在其父节点中的顺序
     *
     * @return 报表节点在其父节点中的顺序
     */
    public Integer getSequence() {
        return this.sequence;
    }

    /**
     * 设置报表节点在其父节点中的顺序
     *
     * @param sequence
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * 获取报表默认展示多少天的数据
     *
     * @return 报表默认展示多少天的数据
     */
    public Integer getDataRange() {
        return this.dataRange;
    }

    /**
     * 设置报表默认展示多少天的数据
     *
     * @param dataRange
     */
    public void setDataRange(Integer dataRange) {
        this.dataRange = dataRange;
    }

    /**
     * 获取布局形式.1横向;2纵向
     *
     * @return 布局形式.1横向;2纵向
     */
    public Integer getLayout() {
        return this.layout;
    }

    /**
     * 设置布局形式.1横向;2纵向
     *
     * @param layout
     */
    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    /**
     * 获取统计列布局形式.1横向;2纵向
     *
     * @return 统计列布局形式.1横向;2纵向
     */
    public Integer getStatColumnLayout() {
        return this.statColumnLayout;
    }

    /**
     * 设置统计列布局形式.1横向;2纵向
     *
     * @param statColumnLayout
     */
    public void setStatColumnLayout(Integer statColumnLayout) {
        this.statColumnLayout = statColumnLayout;
    }

    /**
     * 获取报表SQL语句
     *
     * @return 报表SQL语句
     */
    public String getSqlText() {
        return this.sqlText;
    }

    /**
     * 设置报表SQL语句
     *
     * @param sqlText
     */
    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    /**
     * 获取报表元数据列集合的JSON序列化字符串
     *
     * @return 报表元数据列集合的JSON序列化字符串
     */
    public String getMetaColumns() {
        return this.metaColumns;
    }

    /**
     * 设置报表元数据列集合的JSON序列化字符串
     *
     * @param metaColumns
     */
    public void setMetaColumns(String metaColumns) {
        this.metaColumns = metaColumns;
    }

    /**
     * 获取查询条件列属性集合json字符串
     *
     * @return 查询条件列属性集合json字符串
     */
    public String getQueryParams() {
        return this.queryParams;
    }

    /**
     * 设置查询条件列属性集合json字符串
     *
     * @param queryParams
     */
    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
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
     * 获取创建用户
     *
     * @return 创建用户
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置创建用户
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

    /**
     * 解析json格式的报表元数据列为ReportMetaDataColumn对象集合
     *
     * @return List<ReportMetaDataColumn>
     */
    @JsonIgnore
    public List<ReportMetaDataColumn> getMetaColumnList() {
        if (StringUtils.isBlank(this.metaColumns)) {
            return new ArrayList<ReportMetaDataColumn>(0);
        }
        return JSON.parseArray(this.metaColumns, ReportMetaDataColumn.class);
    }

    /**
     * 解析json格式的报表查询参数为QueryParameterPo对象集合
     *
     * @return List<QueryParameterPo>
     */
    @JsonIgnore
    public List<QueryParameterPo> getQueryParamList() {
        if (StringUtils.isBlank(this.queryParams)) {
            return new ArrayList<QueryParameterPo>(0);
        }
        return JSON.parseArray(this.queryParams, QueryParameterPo.class);
    }
}
