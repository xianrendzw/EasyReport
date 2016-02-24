package com.easytoolsoft.easyreport.scheduler;

import com.easytoolsoft.easyreport.data.util.SpringContextUtils;
import com.easytoolsoft.easyreport.po.ReportingTaskPo;
import com.easytoolsoft.easyreport.service.ReportingTaskService;

import java.util.List;

public class TaskUtils {
    private final static ReportingTaskService service = SpringContextUtils.getBean(ReportingTaskService.class);
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
