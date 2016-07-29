package com.easytoolsoft.easyreport.report.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.pair.TextValuePair;
import com.easytoolsoft.easyreport.engine.data.ReportDataCell;
import com.easytoolsoft.easyreport.engine.data.ReportDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.report.IChartReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class ChartReportService implements IChartReportService {
    @Override
    public Map<String, List<TextValuePair>> getDimColumnMap(ReportDataSet reportDataSet) {
        Map<String, List<String>> map = reportDataSet.getUnduplicatedNonStatColumnDataMap();
        if (map.size() < 1) {
            return new HashMap<>(0);
        }

        List<ReportDataColumn> nonStatColumns = reportDataSet.getNonStatColumns();
        Map<String, List<TextValuePair>> dimColumnMap = new HashMap<>(nonStatColumns.size());
        for (ReportDataColumn column : nonStatColumns) {
            List<TextValuePair> options = new ArrayList<>(map.get(column.getName()).size() + 1);
            options.add(new TextValuePair("全部", "all", true));
            options.addAll(map.get(column.getName()).stream()
                    .map(columnValue -> new TextValuePair(columnValue, columnValue)).collect(Collectors.toList()));
            dimColumnMap.put(column.getName(), options);
        }
        return dimColumnMap;
    }

    @Override
    public JSONArray getStatColumns(ReportDataSet reportDataSet) {
        return this.getJsonArray(reportDataSet.getStatColumns());
    }

    @Override
    public JSONArray getDimColumns(ReportDataSet reportDataSet) {
        return this.getJsonArray(reportDataSet.getNonStatColumns());
    }

    private JSONArray getJsonArray(List<ReportDataColumn> columns) {
        JSONArray jsonArray = new JSONArray(columns.size());
        for (ReportDataColumn column : columns) {
            JSONObject object = new JSONObject();
            object.put("name", column.getName());
            object.put("text", column.getText());
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @Override
    public Map<String, JSONObject> getDataRows(ReportDataSet reportDataSet) {
        Map<String, ReportDataRow> dataRows = reportDataSet.getRowMap();
        List<ReportDataColumn> statColumns = reportDataSet.getStatColumns();
        Map<String, JSONObject> rowMap = new HashMap<>(dataRows.size());

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
