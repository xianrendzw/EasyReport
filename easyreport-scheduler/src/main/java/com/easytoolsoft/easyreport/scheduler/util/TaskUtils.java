package com.easytoolsoft.easyreport.scheduler.util;

import java.util.List;

import com.easytoolsoft.easyreport.meta.domain.Task;
import com.easytoolsoft.easyreport.meta.service.TaskService;

/**
 * @author tomdeng
 */
public class TaskUtils {
    private static TaskService service;
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

    public static void execute(final Task task) {
    }
}
