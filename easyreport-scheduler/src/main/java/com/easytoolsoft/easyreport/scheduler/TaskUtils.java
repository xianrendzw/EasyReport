package com.easytoolsoft.easyreport.scheduler;

import com.easytoolsoft.easyreport.domain.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.schedule.service.impl.TaskService;

import java.util.List;

/**
 * @author tomdeng
 */
public class TaskUtils {
    private final static TaskService service = new TaskService();
    private static List<Task> tasks;

    public static List<Task> getTasks() {
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

    public static void execute(Task task) {
    }
}
