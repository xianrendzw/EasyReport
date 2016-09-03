package com.easytoolsoft.easyreport.data.schedule.po;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class MailTaskOptions extends AbstractTaskOptions {
    private String cc;
}
