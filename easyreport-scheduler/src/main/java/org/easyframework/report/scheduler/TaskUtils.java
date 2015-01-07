package org.easyframework.report.scheduler;

import java.util.List;

import org.easyframework.report.data.util.SpringContextUtils;
import org.easyframework.report.po.ReportingTaskPo;
import org.easyframework.report.service.ReportingTaskService;

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
