package com.easytoolsoft.easyreport.domain.schedule.po;

import lombok.Data;

@Data
public abstract class AbstractTaskOptions {
    private String from;
    private String to;
    private String subject;
}
