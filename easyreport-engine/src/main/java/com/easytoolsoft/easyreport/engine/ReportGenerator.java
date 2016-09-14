package com.easytoolsoft.easyreport.engine;

import com.easytoolsoft.easyreport.engine.data.LayoutType;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import com.easytoolsoft.easyreport.engine.query.Queryer;

/**
 * 报表产生器类
 */
public class ReportGenerator {

    /**
     * @param ds
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(ReportDataSource ds, ReportParameter parameter) {
        return generate(getDataSet(ds, parameter), parameter);
    }

    /**
     * @param queryer
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(Queryer queryer, ReportParameter parameter) {
        return generate(getDataSet(queryer, parameter), parameter);
    }

    /**
     * @param metaDataSet
     * @param parameter
     * @return
     */
    public static ReportTable generate(ReportMetaDataSet metaDataSet, ReportParameter parameter) {
        return generate(getDataSet(metaDataSet, parameter), parameter);
    }

    /**
     * @param ds
     * @param parameter
     * @return
     */
    public static ReportDataSet getDataSet(ReportDataSource ds, ReportParameter parameter) {
        return new DataExecutor(ds, parameter).execute();
    }

    /**
     * @param queryer
     * @param parameter
     * @return
     */
    public static ReportDataSet getDataSet(Queryer queryer, ReportParameter parameter) {
        return new DataExecutor(queryer, parameter).execute();
    }

    /**
     * @param metaDataSet
     * @param parameter
     * @return
     */
    public static ReportDataSet getDataSet(ReportMetaDataSet metaDataSet, ReportParameter parameter) {
        return new DataExecutor(parameter).execute(metaDataSet);
    }

    /**
     * @param dataSet
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(ReportDataSet dataSet, ReportParameter parameter) {
        ReportBuilder builder = createBuilder(dataSet, parameter);
        ReportDirector director = new ReportDirector(builder);
        director.build();
        return builder.getTable();
    }

    private static ReportBuilder createBuilder(ReportDataSet reportDataSet, ReportParameter parameter) {
        if (parameter.getStatColumnLayout() == LayoutType.HORIZONTAL) {
            return new HorizontalStatColumnReportBuilder(reportDataSet, parameter);
        }
        return new VerticalStatColumnReportBuilder(reportDataSet, parameter);
    }
}
