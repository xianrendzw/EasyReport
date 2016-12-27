package com.easytoolsoft.easyreport.scheduler;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    @RequestMapping("/schedule")
    public Response schedule(@RequestParam(value="name", defaultValue="World") String name) {
        return new Response("200","hell world",null);
    }

}
