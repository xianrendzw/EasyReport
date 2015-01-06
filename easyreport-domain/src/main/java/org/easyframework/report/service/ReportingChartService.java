package org.easyframework.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.easyframework.report.engine.data.ReportDataCell;
import org.easyframework.report.engine.data.ReportDataColumn;
import org.easyframework.report.engine.data.ReportDataRow;
import org.easyframework.report.engine.data.ReportDataSet;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 报表图表生成服务类
 * 
 */
@Service
public class ReportingChartService {
	public String buildDimColumnsHtml(ReportDataSet reportDataSet) {
		Map<String, List<String>> map = reportDataSet.getNonStatColumnData();
		if (map.size() < 1)
			return "";

		List<ReportDataColumn> nonStatColumns = reportDataSet.getNonStatColumns();
		StringBuilder htmlText = new StringBuilder("");
		htmlText.append(String.format("<label style=\"width: 120px;\">%s:</label>", "选择维度"));
		for (ReportDataColumn column : nonStatColumns) {
			htmlText.append(String.format("<label style=\"width: 120px;\">%s:</label><select id=\"dim_%s\" name=\"dim_%s\">", column.getText(),
					column.getName(), column.getName()));
			htmlText.append(String.format("<option value=\"%s\">%s</option>", "all", "全部"));
			for (String columnValue : map.get(column.getName())) {
				htmlText.append(String.format("<option value=\"%s\">%s</option>", columnValue, columnValue));
			}
			htmlText.append("</select>");
		}

		return htmlText.toString();
	}

	public JSONArray getStatColumns(ReportDataSet reportDataSet) {
		List<ReportDataColumn> statColumns = reportDataSet.getStatColumns();
		JSONArray jsonArray = new JSONArray(statColumns.size());
		for (ReportDataColumn column : statColumns) {
			JSONObject object = new JSONObject();
			object.put("name", column.getName());
			object.put("text", column.getText());
			jsonArray.add(object);
		}
		return jsonArray;
	}

	public JSONArray getDimColumns(ReportDataSet reportDataSet) {
		List<ReportDataColumn> nonStatColumns = reportDataSet.getNonStatColumns();
		JSONArray jsonArray = new JSONArray(nonStatColumns.size());
		for (ReportDataColumn column : nonStatColumns) {
			JSONObject object = new JSONObject();
			object.put("name", column.getName());
			object.put("text", column.getText());
			jsonArray.add(object);
		}
		return jsonArray;
	}

	public Map<String, JSONObject> getDataRows(ReportDataSet reportDataSet) {
		Map<String, ReportDataRow> dataRows = reportDataSet.getRowMap();
		List<ReportDataColumn> statColumns = reportDataSet.getStatColumns();
		Map<String, JSONObject> rowMap = new HashMap<String, JSONObject>(dataRows.size());

		for (Entry<String, ReportDataRow> set : dataRows.entrySet()) {
			JSONObject object = new JSONObject(statColumns.size());
			for (ReportDataColumn statColumn : statColumns) {
				ReportDataCell cell = set.getValue().getCell(statColumn.getName());
				Object value = cell.getValue();
				object.put(cell.getName(), value == null ? 0 : value);
			}
			rowMap.put(set.getKey(), object);
		}

		return rowMap;
	}
}
