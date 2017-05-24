package com.easytoolsoft.easyreport.engine;

import com.easytoolsoft.easyreport.engine.data.AbstractReportDataSet;
import com.easytoolsoft.easyreport.engine.data.LayoutType;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import com.easytoolsoft.easyreport.engine.query.Queryer;

/**
 * 报表产生器类
 *
 * @author tomdeng
 */
public class ReportGenerator {

    /**
     * @param ds
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(final ReportDataSource ds, final ReportParameter parameter) {
        return generate(getDataSet(ds, parameter), parameter);
    }

    /**
     * @param queryer
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(final Queryer queryer, final ReportParameter parameter) {
        return generate(getDataSet(queryer, parameter), parameter);
    }

    /**
     * @param metaDataSet
     * @param parameter
     * @return
     */
    public static ReportTable generate(final ReportMetaDataSet metaDataSet, final ReportParameter parameter) {
        return generate(getDataSet(metaDataSet, parameter), parameter);
    }

    /**
     * @param ds
     * @param parameter
     * @return
     */
    public static AbstractReportDataSet getDataSet(final ReportDataSource ds, final ReportParameter parameter) {
        return new DataExecutor(ds, parameter).execute();
    }

    /**
     * @param queryer
     * @param parameter
     * @return
     */
    public static AbstractReportDataSet getDataSet(final Queryer queryer, final ReportParameter parameter) {
        return new DataExecutor(queryer, parameter).execute();
    }

    /**
     * @param metaDataSet
     * @param parameter
     * @return
     */
    public static AbstractReportDataSet getDataSet(final ReportMetaDataSet metaDataSet,
                                                   final ReportParameter parameter) {
        return new DataExecutor(parameter).execute(metaDataSet);
    }

    /**
     * @param dataSet
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(final AbstractReportDataSet dataSet, final ReportParameter parameter) {
        final ReportBuilder builder = createBuilder(dataSet, parameter);
        final ReportDirector director = new ReportDirector(builder);
        director.build();
        return builder.getTable();
    }

    private static ReportBuilder createBuilder(final AbstractReportDataSet reportDataSet,
                                               final ReportParameter parameter) {
        if (parameter.getStatColumnLayout() == LayoutType.HORIZONTAL) {
            return new HorizontalStatColumnReportBuilder(reportDataSet, parameter);
        }
        return new VerticalStatColumnReportBuilder(reportDataSet, parameter);
    }
}
