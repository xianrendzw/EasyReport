package com.easytoolsoft.easyreport.dao;

import com.easytoolsoft.easyreport.data.PageInfo;
import com.easytoolsoft.easyreport.data.SortType;
import com.easytoolsoft.easyreport.data.criterion.Restrictions;
import com.easytoolsoft.easyreport.data.criterion.operands.Bracket;
import com.easytoolsoft.easyreport.data.jdbc.BaseDao;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;
import com.easytoolsoft.easyreport.engine.query.QueryerFactory;
import com.easytoolsoft.easyreport.po.ReportingPo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReportingDao提供报表信息表表(reporting)的相关数据访问操作的类。
 *
 * @author Tom Deng
 */
@Repository
public class ReportingDao extends BaseDao<ReportingPo> {
    public ReportingDao() {
        super(ReportingPo.EntityName, ReportingPo.Id);
    }

    public boolean exists(int dsId) {
        String condition = Restrictions.equal(ReportingPo.DsId, "?").toString();
        Object[] args = new Object[]{dsId};
        return this.count(condition, args) > 0;
    }

    public boolean exists(String createUser, int id) {
        String condition = Restrictions.equal(ReportingPo.CreateUser, "?")
                .append(Restrictions.And)
                .append(Restrictions.equal(ReportingPo.Id, "?")).toString();
        Object[] args = new Object[]{createUser, id};
        return this.count(condition, args) > 0;
    }

    public boolean exists(int pid, int id, String name) {
        String condition = Restrictions.equal(ReportingPo.Pid, "?")
                .append(Restrictions.And)
                .append(Restrictions.equal(ReportingPo.Name, "?"))
                .append(Restrictions.And)
                .append(Restrictions.notEqual(ReportingPo.Id, "?")).toString();
        Object[] args = new Object[]{pid, name, id};
        return this.count(condition, args) > 0;
    }

    public boolean existsByUid(String uid) {
        String condition = Restrictions.equal(ReportingPo.Uid, "?").toString();
        Object[] args = new Object[]{uid};
        return this.count(condition, args) > 0;
    }

    public int countChildBy(int pid) {
        String condition = Restrictions.equal(ReportingPo.Pid, pid).toString();
        return this.count(condition);
    }

    public int countChildBy(int pid, String name) {
        String condition = Restrictions.equal(ReportingPo.Pid, "?")
                .append(Restrictions.And)
                .append(Restrictions.bracket(Bracket.Left))
                .append(Restrictions.equal(ReportingPo.Name, "?"))
                .append(Restrictions.Or)
                .append(Restrictions.like(ReportingPo.Name, String.format("%s_复件%%", name)))
                .append(Restrictions.bracket(Bracket.Rgiht)).toString();
        Object[] args = new Object[]{pid, name};
        return this.count(condition, args);
    }

    public int queryStatus(int id) {
        ReportingPo po = this.queryByKey(id, ReportingPo.Status);
        return po == null ? -1 : po.getStatus();
    }

    public String querySqlText(int id) {
        ReportingPo po = this.queryByKey(id, ReportingPo.Status);
        return po == null ? "" : po.getSqlText();
    }

    public ReportingPo queryByUid(String uid) {
        String condition = Restrictions.equal(ReportingPo.Uid, "?").toString();
        Object[] args = new Object[]{uid};
        return this.queryOne(condition, args);
    }

    public String queryPath(int pid) {
        ReportingPo po = this.queryByKey(pid, ReportingPo.Path);
        return po == null ? "" : po.getPath();
    }

    public int queryId(String uid) {
        String condition = Restrictions.equal(ReportingPo.Uid, "?").toString();
        Object[] args = new Object[]{uid};
        ReportingPo po = this.queryOne(condition, args, ReportingPo.Id);
        return po == null ? 0 : po.getId();
    }

    public List<ReportingPo> queryAllChild(int id) {
        String condition = Restrictions.equal(ReportingPo.Id, "?")
                .append(Restrictions.like(ReportingPo.Path, "'" + id + ",%'")).toString();
        Object[] args = new Object[]{id};
        return this.query(condition, args);
    }

    public List<ReportingPo> queryByPid(int pid) {
        String condition = Restrictions.equal(ReportingPo.Pid, "?")
                .append(Restrictions.orderBy(SortType.ASC, ReportingPo.Sequence))
                .toString();
        Object[] args = new Object[]{pid};
        return this.query(condition, args);
    }

    public List<ReportingPo> queryByPage(String fieldName, String keyword, PageInfo page) {
        String condition = "";
        if (keyword != null && keyword.trim().length() > 0) {
            condition = Restrictions.like(fieldName, "%" + keyword + "%").toString();
        }
        String[] columnNames = new String[]{
                ReportingPo.Id, ReportingPo.Pid, ReportingPo.Uid, ReportingPo.Path,
                ReportingPo.Name, ReportingPo.CreateUser, ReportingPo.CreateTime
        };
        return this.query(condition, page, columnNames);
    }

    public int updateUid(int id, String uid) {
        String condition = Restrictions.equal(ReportingPo.Id, "?").toString();
        String[] columnNames = new String[]{ReportingPo.Uid};
        Object[] args = new Object[]{uid, id};
        return this.update(condition, args, columnNames);
    }

    public int updateTreeNode(ReportingPo po) {
        String[] columnNames = new String[]{
                ReportingPo.Name, ReportingPo.Flag,
                ReportingPo.HasChild, ReportingPo.Status,
                ReportingPo.Sequence, ReportingPo.Comment
        };
        return this.update(po, columnNames);
    }

    public int updateQueryParams(int id, String queryParams) {
        String condition = Restrictions.equal(ReportingPo.Id, "?").toString();
        String[] columnNames = new String[]{ReportingPo.QueryParams};
        Object[] args = new Object[]{queryParams, id};
        return this.update(condition, args, columnNames);
    }

    public int updatePath(int id, String path) {
        String condition = Restrictions.equal(ReportingPo.Id, "?").toString();
        String[] columnNames = new String[]{ReportingPo.Path};
        Object[] args = new Object[]{path, id};
        return this.update(condition, args, columnNames);
    }

    public int updateHasChild(int pid, boolean hasChild) {
        String condition = Restrictions.equal(ReportingPo.Id, "?").toString();
        String[] columnNames = new String[]{ReportingPo.HasChild};
        Object[] args = new Object[]{hasChild, pid};
        return this.update(condition, args, columnNames);
    }

    public int updatePid(int id, int pid) {
        String condition = Restrictions.equal(ReportingPo.Id, "?").toString();
        String[] columnNames = new String[]{ReportingPo.Pid};
        Object[] args = new Object[]{pid, id};
        return this.update(condition, args, columnNames);
    }

    public int updateCreateUser(int id, String createUser, String comment) {
        String condition = Restrictions.equal(ReportingPo.Id, "?").toString();
        String[] columnNames = new String[]{ReportingPo.CreateUser, ReportingPo.Comment};
        Object[] args = new Object[]{createUser, comment, id};
        return this.update(condition, args, columnNames);
    }

    public int updateByUid(ReportingPo po) {
        String[] columnNames = new String[]{
                ReportingPo.DsId, ReportingPo.Name, ReportingPo.DataRange, ReportingPo.Layout,
                ReportingPo.SqlText, ReportingPo.MetaColumns, ReportingPo.QueryParams, ReportingPo.Comment
        };
        String condition = Restrictions.equal(ReportingPo.Uid, "?").toString();
        Object[] args = new Object[]{po.getUid()};
        return this.update(po, condition, args, columnNames);
    }

    public List<ReportMetaDataColumn> executeSqlText(String url, String user, String password, String sqlText) {
        return QueryerFactory.create(url, user, password).parseMetaDataColumns(sqlText);
    }

    public List<ReportQueryParamItem> executeQueryParamSqlText(String url, String user, String password, String sqlText) {
        return QueryerFactory.create(url, user, password).parseQueryParamItems(sqlText);
    }
}