package org.easyframework.report.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.easyframework.report.dao.ReportingDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.engine.ReportGenerator;
import org.easyframework.report.engine.data.ReportDataSet;
import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportParameter;
import org.easyframework.report.engine.data.ReportSqlTemplate;
import org.easyframework.report.entity.DataSource;
import org.easyframework.report.entity.Reporting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报表生成服务类
 */
@Service
public class ReportingGenerationService extends BaseService<ReportingDao, Reporting> {
	@Resource
	private DataSourceService dataSourceService;
	@Resource
	private ConfigDictService configDictService;

	@Autowired
	public ReportingGenerationService(ReportingDao dao) {
		super(dao);
	}

	public String getHtmlTable(int id) {
		Reporting report = this.dao.queryByKey(id);
		Map<String, Object> parameters = this.getFormParameters(new HashMap<String, Object>());
		return this.getHtmlTable(report, parameters);
	}

	public String getHtmlTable(String uid, Map<String, Object> parameters) {
		Reporting report = this.dao.queryByUid(uid);
		return this.getHtmlTable(report, parameters);
	}

	public String getHtmlTable(Reporting report, Map<String, Object> parameters) {
		DataSource ds = this.dataSourceService.getById(report.getDsId());
		ReportDataSource reportDs = new ReportDataSource(ds.getJdbcUrl(), ds.getUser(), ds.getPassword());
		return ReportGenerator.generate(reportDs, this.createReportParameter(report, parameters));
	}

	public ReportDataSet getReportDataSet(Reporting report, Map<String, Object> parameters) {
		DataSource ds = this.dataSourceService.getById(report.getDsId());
		ReportDataSource reportDs = new ReportDataSource(ds.getJdbcUrl(), ds.getUser(), ds.getPassword());
		return ReportGenerator.getDataSet(reportDs, this.createReportParameter(report, parameters));
	}

	private ReportParameter createReportParameter(Reporting report, Map<String, Object> parameters) {
		String sqlText = new ReportSqlTemplate(report.getSqlText(), parameters).execute();
		Set<String> displayedStatColumns = this.getDisplayedStatColumns(parameters);
		return new ReportParameter(report.getName(),
				report.getLayout(),
				report.getStatColumnLayout(),
				sqlText,
				report.getMetaColumns(),
				displayedStatColumns);
	}

	private Set<String> getDisplayedStatColumns(Map<String, Object> parameters) {
		Set<String> checkedSet = new HashSet<String>();
		String checkedColumnNames = parameters.get("statColumns").toString();
		if (StringUtils.isNotBlank(checkedColumnNames)) {
			String[] columnNames = StringUtils.split(checkedColumnNames, ',');
			for (String columnName : columnNames) {
				if (!checkedSet.contains(columnName)) {
					checkedSet.add(columnName);
				}
			}
		}
		return checkedSet;
	}

	/**
	 * 获取查询条件参数集合
	 *
	 * @param reqParameterMap HttpServletRequest.getParameterMap
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getFormParameters(Map<?, ?> reqParameterMap) {
		return null;
	}
}