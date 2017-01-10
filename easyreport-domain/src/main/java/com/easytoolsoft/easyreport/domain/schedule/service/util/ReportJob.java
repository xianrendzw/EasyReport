package com.easytoolsoft.easyreport.domain.schedule.service.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.easytoolsoft.easyreport.domain.metadata.po.Report;
import com.easytoolsoft.easyreport.domain.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.metadata.service.impl.ReportService;
import com.easytoolsoft.easyreport.domain.report.impl.TableReportService;
import com.easytoolsoft.easyreport.domain.schedule.service.ITaskService;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportTable;

public class ReportJob implements org.quartz.Job {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private TableReportService tableReportService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        String taskid = context.getJobDetail().getKey().getName().substring(4);
        this.execute(Integer.parseInt(taskid));
    }

    public void execute(int taskid) {
        Task task = taskService.getById(taskid);
        String[] reportids = task.getReportIds().split(",");
        String template_html_start = "<html><head><head><style type='text/css'> "+"\n"
        		+"body,table{font-size:12px;}"+ "\n"
        		+"table {color: #333;font-family: Helvetica, Arial, sans-serif;width: 640px;border-collapse:collapse; border-spacing: 0;}"+"\n"
        		+"td, th {border: 1px solid blue; height: 30px;transition: all 0.3s; }"+"\n"
        		+"th {background: #DFDFDF; font-weight: bold;}"+"\n"
        		+"td {background: #FAFAFA;text-align: center;}"+"\n" 
        		+"tr:nth-child(even) td { background: #F1F1F1; }" +"\n"
        		+"tr:nth-child(odd) td { background: #FEFEFE; }"+"\n"
        		+"tr td:hover { background: #666; color: #FFF; }" +"\n"
        		+"</style></head><body>";
        String report_html = template_html_start+task.getTemplate()+"</body></html>";
        int index = 1;
        for (String report_id : reportids) {
            Report po = reportService.getById(Integer.parseInt(report_id));
            String metaColumns = getMetatColumns(po.getMetaColumns());
            HashMap<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("isMergeRow", "off");
            paraMap.put("isRowSpan", "true");
            paraMap.put("name", po.getName());
            paraMap.put("uid", po.getUid());
            paraMap.put("statColumns", metaColumns);
            paraMap.put("checkAllStatColumn", "on");

            report_html=report_html.replaceAll("\\$\\{table" + index + "\\}", generate(po.getUid(), paraMap).getHtmlText().replaceAll("><", ">\n<"));
            index++;
        }
        

        String json = task.getOptions();
        ScheduleOption options = JSON.parseObject(json, ScheduleOption.class);
        String data = "From: "+options.getFrom()+"\n";
        data+="To: "+options.getTo()+"\n";
        data+="Cc: "+options.getCc()+"\n";
        data+="Content-type: text/html;charset=UTF-8\n";
        data+="Subject: "+options.getSubject()+"\n";
        data+=report_html;
        
        File file = new File("mail/mail_" + System.currentTimeMillis());
        try {
            FileUtils.writeStringToFile(file, data, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // task.getTemplate()
        System.out.println("SimpleJob says: " + task + " executing at " + new Date());
    }

    private String getMetatColumns(String metaColumnsJson) {
        List<ReportMetaDataColumn> metaColumns = reportService.parseMetaColumns(metaColumnsJson);
        HashMap<String, String> map = new HashMap<String, String>();
        String result = "";
        List<String> columnNameList = new ArrayList<String>();
        for (ReportMetaDataColumn column : metaColumns) {
            columnNameList.add(column.getName());
        }
        return StringUtils.join(columnNameList, ",");
    }

    private ReportTable generate(String uid, Map<String, Object> parameters) {
        Report report = reportService.getByUid(uid);
        return tableReportService.getReportTable(report, parameters);
    }
    
    private static String formatHtml(String str) throws Exception {  
        Document document = null;  
        document = DocumentHelper.parseText(str);  
      
        OutputFormat format = OutputFormat.createPrettyPrint();  
        format.setEncoding("utf-8");  
        StringWriter writer = new StringWriter();  
      
        HTMLWriter htmlWriter = new HTMLWriter(writer, format);  
      
        htmlWriter.write(document);  
        htmlWriter.close();  
        return writer.toString();  
    }  
}
