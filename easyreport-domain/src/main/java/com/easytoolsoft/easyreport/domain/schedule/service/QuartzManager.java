package com.easytoolsoft.easyreport.domain.schedule.service;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.easytoolsoft.easyreport.data.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.schedule.service.impl.TaskService;

@Component
@Scope(value = "singleton")
public class QuartzManager {
    private SchedulerFactory sf = new StdSchedulerFactory();
    @Autowired
    private TaskService taskService;
    

    public void reScheduleJob() {
        List<Task> tasks = taskService.getAll();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                configQuatrz(task);
            }
        }
        System.out.println("reschedule jobs end");
    }
    
    public void triggerTask(int taskid){
    	new ReportJob().execute(taskid);
    }
    
    

    public boolean configQuatrz(Task task) {
        boolean result = false;
        try {
            CronTrigger trigger = (CronTrigger) sf.getScheduler().getTrigger(new TriggerKey("Trigger_"+task.getId(), Scheduler.DEFAULT_GROUP));
            if (trigger != null) {
                change(task,  trigger);
            } else {
                    this.createCronTriggerBean(task);
            }
            result = true;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public void change(Task task, CronTrigger trigger) throws Exception {
        if(task.getId()>0){
            if (!trigger.getCronExpression().equalsIgnoreCase(task.getCronExpr())) {
                ((org.quartz.impl.triggers.CronTriggerImpl) trigger).setCronExpression(task.getCronExpr());
                sf.getScheduler().rescheduleJob(new TriggerKey("Trigger_"+task.getId(), Scheduler.DEFAULT_GROUP), trigger);
            }
        }else {
            sf.getScheduler().pauseTrigger(new TriggerKey("Trigger_"+task.getId(), Scheduler.DEFAULT_GROUP));
            sf.getScheduler().unscheduleJob(new TriggerKey("Trigger_"+task.getId(), Scheduler.DEFAULT_GROUP));
            sf.getScheduler().deleteJob(trigger.getJobKey());
        }

    }

    /**
     * Create Cron Trigger
     * @param task
     * @throws SchedulerException 
     * @throws Exception
     */
    public void createCronTriggerBean(Task task) throws SchedulerException {

        
        JobDetail job = JobBuilder.newJob(ReportJob.class)
                .withIdentity("Job_"+task.getId(), Scheduler.DEFAULT_GROUP)
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("Trigger_"+task.getId(), Scheduler.DEFAULT_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpr()))
                .build();

            sf.getScheduler().scheduleJob(job, trigger);
            sf.getScheduler().start();
    }
    
    public static final void main(String[] args){
        try {
            new QuartzManager().createCronTriggerBean(null);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
