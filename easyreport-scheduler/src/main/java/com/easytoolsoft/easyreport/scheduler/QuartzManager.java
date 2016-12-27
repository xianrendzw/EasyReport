package com.easytoolsoft.easyreport.scheduler;

import java.text.ParseException;
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

import com.easytoolsoft.easyreport.data.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.schedule.service.ITaskService;

public class QuartzManager {
    SchedulerFactory sf = new StdSchedulerFactory();

    @Autowired
    private ITaskService taskService;

    public void reScheduleJob() throws Exception, ParseException {
        List<Task> tasks = taskService.getAll();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                configQuatrz(task);
            }
        }
    }

    public boolean configQuatrz(Task task) {
        boolean result = false;
        try {
            CronTrigger trigger = (CronTrigger) sf.getScheduler().getTrigger(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP));
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
                sf.getScheduler().rescheduleJob(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP), trigger);
            }
        }else {
            sf.getScheduler().pauseTrigger(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP));
            sf.getScheduler().unscheduleJob(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP));
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
                .withIdentity("job1", "group1")
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
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
