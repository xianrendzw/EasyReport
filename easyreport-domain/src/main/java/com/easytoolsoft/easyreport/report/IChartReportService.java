package com.easytoolsoft.easyreport.report;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.pair.TextValuePair;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;

import java.util.List;
import java.util.Map;

/**
 * 图表报表服务类
 *
 * @author Tom Deng
 */
public interface IChartReportService {
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
