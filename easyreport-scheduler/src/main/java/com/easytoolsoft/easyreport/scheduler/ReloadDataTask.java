package com.easytoolsoft.easyreport.scheduler;

import java.util.TimerTask;

/**
 * @author tomdeng
 */
public class ReloadDataTask extends TimerTask {
    @Override
    public void run() {
        TaskUtils.reloadTasks();
    }
}
