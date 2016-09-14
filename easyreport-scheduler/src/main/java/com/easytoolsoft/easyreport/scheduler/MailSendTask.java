package com.easytoolsoft.easyreport.scheduler;

import com.easytoolsoft.easyreport.data.schedule.po.Task;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.List;
import java.util.TimerTask;

@Slf4j
public class MailSendTask extends TimerTask {
    @Override
    public void run() {
        List<Task> tasks = TaskUtils.getTasks();
        for (Task task : tasks) {
            try {
                if (isRunnable(task)) {
                    TaskUtils.execute(task);
                    log.info("执行完成!");
                }
            } catch (Exception ex) {
                log.error(task.getComment(), ex);
            }
        }
    }

    public boolean isRunnable(Task rt) throws ParseException {
        return false;
    }

}
