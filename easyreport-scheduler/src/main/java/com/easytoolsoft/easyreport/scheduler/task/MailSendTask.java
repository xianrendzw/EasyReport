package com.easytoolsoft.easyreport.scheduler.task;

import java.text.ParseException;
import java.util.List;
import java.util.TimerTask;

import com.easytoolsoft.easyreport.meta.domain.Task;
import com.easytoolsoft.easyreport.scheduler.util.TaskUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tomdeng
 */
@Slf4j
public class MailSendTask extends TimerTask {
    @Override
    public void run() {
        final List<Task> tasks = TaskUtils.getTasks();
        for (final Task task : tasks) {
            try {
                if (isRunnable(task)) {
                    TaskUtils.execute(task);
                    log.info("执行完成!");
                }
            } catch (final Exception ex) {
                log.error(task.getComment(), ex);
            }
        }
    }

    public boolean isRunnable(final Task rt) throws ParseException {
        return false;
    }

}
