package com.easyreport.scheduler;

import java.util.List;

import com.easyreport.data.util.SpringContextUtils;
import com.easyreport.po.ReportingTaskPo;
import com.easyreport.service.ReportingTaskService;

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
