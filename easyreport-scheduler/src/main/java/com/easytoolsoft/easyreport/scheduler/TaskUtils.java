package com.easytoolsoft.easyreport.scheduler;

import com.easytoolsoft.easyreport.data.util.SpringContextUtils;
import com.easytoolsoft.easyreport.schedule.po.ReportingTaskPo;
import com.easytoolsoft.easyreport.schedule.service.impl.TaskService;

import java.util.List;

public class TaskUtils {
    private final static TaskService service = SpringContextUtils.getBean(TaskService.class);
    private static List<ReportingTaskPo> tasks;

    public static List<ReportingTaskPo> getTasks() {
        if (tasks == null) {
            tasks = service.getAll();
        }
        return tasks;
    }

    public static void reloadTasks() {
        synchronized (tasks) {
            tasks = service.getAll();
        }
    }

    public static void execute(ReportingTaskPo taskPo) {
    }
}
