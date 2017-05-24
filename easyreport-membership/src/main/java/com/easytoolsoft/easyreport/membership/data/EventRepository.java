package com.easytoolsoft.easyreport.membership.data;

import com.easytoolsoft.easyreport.membership.domain.Event;
import com.easytoolsoft.easyreport.membership.domain.example.EventExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统事件或日志(easyreport_member_event表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("EventRepository")
public interface EventRepository extends CrudRepository<Event, EventExample, Integer> {
}
