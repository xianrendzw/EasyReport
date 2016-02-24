package com.easytoolsoft.easyreport.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.viewmodel.TextValuePair;
import com.easytoolsoft.easyreport.engine.data.ReportDataCell;
import com.easytoolsoft.easyreport.engine.data.ReportDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 报表图表生成服务类
 */
@Service
public class ReportingChartService {
    public Map<String, List<TextValuePair>> getDimColumnMap(ReportDataSet reportDataSet) {
        Map<String, List<String>> map = reportDataSet.getUnduplicatedNonStatColumnDataMap();
        if (map.size() < 1) {
            return new HashMap<String, List<TextValuePair>>(0);
        }

        List<ReportDataColumn> nonStatColumns = reportDataSet.getNonStatColumns();
        Map<String, List<TextValuePair>> dimColumnMap = new HashMap<String, List<TextValuePair>>(nonStatColumns.size());
        for (ReportDataColumn column : nonStatColumns) {
            List<TextValuePair> options = new ArrayList<TextValuePair>(map.get(column.getName()).size() + 1);
            options.add(new TextValuePair("全部", "all", true));
            for (String columnValue : map.get(column.getName())) {
                options.add(new TextValuePair(columnValue, columnValue));
            }
            dimColumnMap.put(column.getName(), options);
        }

        return dimColumnMap;
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
