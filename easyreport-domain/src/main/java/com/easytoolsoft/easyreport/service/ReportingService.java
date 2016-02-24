package com.easytoolsoft.easyreport.service;

import com.easytoolsoft.easyreport.dao.ReportingDao;
import com.easytoolsoft.easyreport.data.PageInfo;
import com.easytoolsoft.easyreport.data.jdbc.BaseService;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.po.DataSourcePo;
import com.easytoolsoft.easyreport.po.ReportingPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 报表信息表服务类
 */
@Service
public class ReportingService extends BaseService<ReportingDao, ReportingPo> {
    @Resource
    private DataSourceService dataSourceService;
    @Resource
    private ConfigDictService configDictService;

    @Autowired
    public ReportingService(ReportingDao dao) {
        super(dao);
    }

    public int addReport(ReportingPo entity) {
        entity.setUid(UUID.randomUUID().toString());
        int newId = this.dao.insertWithId(entity);
        this.setHasChild(entity.getPid());
        this.dao.updatePath(newId, this.getPath(entity.getPid(), newId));
        return newId;
    }

    public boolean editReport(ReportingPo entity) {
        String[] columnNames = new String[]{
                ReportingPo.DsId, ReportingPo.Name, ReportingPo.Status,
                ReportingPo.Sequence, ReportingPo.DataRange, ReportingPo.Layout,
                ReportingPo.StatColumnLayout, ReportingPo.SqlText, ReportingPo.MetaColumns
        };
        return this.edit(entity, entity.getId(), columnNames);
    }

    public boolean setQueryParams(int id, String queryParamJson) {
        return this.dao.updateQueryParams(id, queryParamJson) > 0;
    }

    public ReportingPo getByUid(String uid) {
        return this.dao.queryByUid(uid);
    }

    public List<ReportingPo> getByPid(int pid) {
        return this.dao.queryByPid(pid);
    }

    public String getSqlText(int id) {
        ReportingPo entity = this.dao.queryByKey(id, ReportingPo.SqlText);
        return entity == null ? "" : entity.getSqlText();
    }

    public List<ReportingPo> getByPage(String fieldName, String keyword, PageInfo page) {
        return this.dao.queryByPage(fieldName, keyword, page);
    }

    public boolean hasChild(int pid) {
        return this.dao.countChildBy(pid) > 0;
    }

    public boolean remove(int id, int pid) {
        this.dao.deleteByKey(id);
        if (this.hasChild(pid)) {
            return true;
        }
        return this.dao.updateHasChild(pid, false) > 0;
    }

    public String getPath(int pid, int id) {
        if (pid <= 0) {
            return "" + id;
        }
        return this.dao.queryPath(pid) + "," + id;
    }

    public void setHasChild(int pid) {
        if (pid > 0) {
            this.dao.updateHasChild(pid, true);
        }
    }

    public List<ReportMetaDataColumn> getReportMetaDataColumns(int dsId, String sqlText) {
        DataSourcePo ds = this.dataSourceService.getById(dsId);
        return this.getReportMetaDataColumns(ds, sqlText);
    }

    public List<ReportMetaDataColumn> getReportMetaDataColumns(DataSourcePo ds, String sqlText) {
        List<ReportMetaDataColumn> metaDataColumns = this.dao.executeSqlText(ds.getJdbcUrl(), ds.getUser(), ds.getPassword(), sqlText);
        Map<String, ReportMetaDataColumn> commonColumnMap = this.configDictService.getCommonColumns();
        Map<String, ReportMetaDataColumn> commonOptionColumnMap = this.configDictService.getCommonOptionalColumns();

        for (ReportMetaDataColumn column : metaDataColumns) {
            String columnName = column.getName();
            if (commonColumnMap.containsKey(columnName)) {
                ReportMetaDataColumn commonColumn = commonColumnMap.get(columnName);
                column.setType(commonColumn.getType().getValue());
                column.setText(commonColumn.getText());
            }
            if (commonOptionColumnMap.containsKey(columnName)) {
                column.setOptional(true);
                column.setType(commonOptionColumnMap.get(columnName).getType().getValue());
            }
        }

        return metaDataColumns;
    }

    public void explainSqlText(int dsId, String sqlText) {
        DataSourcePo ds = this.dataSourceService.getById(dsId);
        this.dao.executeSqlText(ds.getJdbcUrl(), ds.getUser(), ds.getPassword(), sqlText);
    }
}