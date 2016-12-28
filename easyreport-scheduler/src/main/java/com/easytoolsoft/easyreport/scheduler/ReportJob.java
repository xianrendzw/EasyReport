package com.easytoolsoft.easyreport.scheduler;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.easytoolsoft.easyreport.data.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.schedule.service.ITaskService;

public class ReportJob implements org.quartz.Job{
    @Autowired
    private ITaskService taskService;
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String  taskid = context.getJobDetail().getKey().getName().substring(4);
        this.execute(Integer.parseInt(taskid));
    }
    
    public void execute(int taskid){
    	 Task task = taskService.getById(taskid);
         System.out.println("SimpleJob says: " + task + " executing at " + new Date());
    }
}
