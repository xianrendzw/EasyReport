package com.easytoolsoft.easyreport.scheduler;

import java.util.TimerTask;

public class ReloadDataTask extends TimerTask {
    @Override
    public void run() {
        TaskUtils.reloadTasks();
    }
}
