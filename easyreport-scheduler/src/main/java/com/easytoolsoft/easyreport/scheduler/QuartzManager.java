package com.easytoolsoft.easyreport.scheduler;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import com.easytoolsoft.easyreport.data.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.schedule.service.ITaskService;

public class QuartzManager implements BeanFactoryAware {
    private Scheduler scheduler;
    private static BeanFactory beanFactory = null;

    @Autowired
    private ITaskService taskService;

    @SuppressWarnings("unused")
    private void reScheduleJob() throws Exception, ParseException {
        List<Task> tasks = taskService.getAll();

        // this.getConfigQuartz();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                configQuatrz(task);
            }
        }
    }

    public boolean configQuatrz(Task task) {
        boolean result = false;
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP));
            // 如果计划任务已存在则调用修改方法
            if (trigger != null) {
                change(task, trigger);
            } else {
//                // 如果计划任务不存在并且数据库里的任务状态为可用时,则创建计划任务
//                if (task.getState().equals("1")) {
//                    this.createCronTriggerBean(tbcq);
//                }
            }
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }

    public void change(Task task, CronTrigger trigger) throws Exception {
        if(task.getId()>0){
            if (!trigger.getCronExpression().equalsIgnoreCase(task.getCronExpr())) {
                CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
                ((org.quartz.impl.triggers.CronTriggerImpl) trigger).setCronExpression(task.getCronExpr());
                scheduler.rescheduleJob(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP), trigger);
            }
        }else {
            // 不可用
            scheduler.pauseTrigger(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP));// 停止触发器
            scheduler.unscheduleJob(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP));// 移除触发器
            JobKey jobKey;
            scheduler.deleteJob(jobKey)
            deleteJob(new TriggerKey(task.getId()+"", Scheduler.DEFAULT_GROUP));// 删除任务
            log.info(new Date() + ": 删除" + tbcq.getTriggername() + "计划任务");

        }

    }

    /**
     * 创建/添加计划任务
     * 
     * @param tbcq 计划任务配置对象
     * @throws Exception
     */
    public void createCronTriggerBean(Wsdoc tbcq) throws Exception {
        // 新建一个基于Spring的管理Job类
        MethodInvokingJobDetailFactoryBean mjdfb = new MethodInvokingJobDetailFactoryBean();
        mjdfb.setName(tbcq.getJobdetailname());// 设置Job名称
        // 如果定义的任务类为Spring的定义的Bean则调用 getBean方法
        if (tbcq.getIsspringbean().equals("1")) {
            mjdfb.setTargetObject(beanFactory.getBean(tbcq.getTargetobject()));// 设置任务类
        } else {
            // 否则直接new对象
            mjdfb.setTargetObject(Class.forName(tbcq.getTargetobject()).newInstance());// 设置任务类
        }

        mjdfb.setTargetMethod(tbcq.getMethodname());// 设置任务方法
        mjdfb.setConcurrent(tbcq.getConcurrent().equals("0") ? false : true); // 设置是否并发启动任务
        mjdfb.afterPropertiesSet();// 将管理Job类提交到计划管理类
        // 将Spring的管理Job类转为Quartz管理Job类
        JobDetail jobDetail = new JobDetail();
        jobDetail = (JobDetail) mjdfb.getObject();
        jobDetail.setName(tbcq.getJobdetailname());
        scheduler.addJob(jobDetail, true); // 将Job添加到管理类
        // 新一个基于Spring的时间类
        CronTriggerBean c = new CronTriggerBean();
        c.setCronExpression(tbcq.getCronexpression());// 设置时间表达式
        c.setName(tbcq.getTriggername());// 设置名称
        c.setJobDetail(jobDetail);// 注入Job
        c.setJobName(tbcq.getJobdetailname());// 设置Job名称
        scheduler.scheduleJob(c);// 注入到管理类
        scheduler.rescheduleJob(tbcq.getTriggername(), Scheduler.DEFAULT_GROUP, c);// 刷新管理类
        log.info(new Date() + ": 新建" + tbcq.getTriggername() + "计划任务");
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setBeanFactory(BeanFactory factory) throws BeansException {
        this.beanFactory = factory;

    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

}
