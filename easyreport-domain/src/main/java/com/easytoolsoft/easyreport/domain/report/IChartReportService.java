package com.easytoolsoft.easyreport.domain.report;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.pair.TextValuePair;
import com.easytoolsoft.easyreport.engine.data.AbstractReportDataSet;

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
    Map<String, List<TextValuePair>> getDimColumnMap(AbstractReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    JSONArray getStatColumns(AbstractReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    JSONArray getDimColumns(AbstractReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    Map<String, JSONObject> getDataRows(AbstractReportDataSet reportDataSet);
}
