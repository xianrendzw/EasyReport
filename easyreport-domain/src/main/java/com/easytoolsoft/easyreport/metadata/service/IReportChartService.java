package com.easytoolsoft.easyreport.metadata.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.pair.TextValuePair;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;

import java.util.List;
import java.util.Map;

/**
 * 报表图表生成服务类
 *
 * @author Tom Deng
 */
public interface IReportChartService {
    /**
     * @param reportDataSet
     * @return
     */
    Map<String, List<TextValuePair>> getDimColumnMap(ReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    JSONArray getStatColumns(ReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    JSONArray getDimColumns(ReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    Map<String, JSONObject> getDataRows(ReportDataSet reportDataSet);
}
