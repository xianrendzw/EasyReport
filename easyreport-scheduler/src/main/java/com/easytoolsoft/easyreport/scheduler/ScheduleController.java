package com.easytoolsoft.easyreport.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {
	@Autowired
	private QuartzManager quartz;
    @RequestMapping("/schedule")
    public Response schedule() {
    	quartz.reScheduleJob();
        return new Response("200","hell world",null);
    }
    
    @RequestMapping("/task")
    public Response schedule(@RequestParam(value="taskid", defaultValue="0") Integer taskid) {
    	quartz.triggerTask(taskid);
        return new Response("200","hell world",null);
    }

}
