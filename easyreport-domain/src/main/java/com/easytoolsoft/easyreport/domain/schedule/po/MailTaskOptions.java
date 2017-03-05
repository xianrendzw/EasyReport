package com.easytoolsoft.easyreport.domain.schedule.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author tomdeng
 */
@SuppressWarnings("serial")
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailTaskOptions extends AbstractTaskOptions {
    private String cc;
}
