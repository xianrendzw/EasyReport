package com.easytoolsoft.easyreport.meta.data;

import com.easytoolsoft.easyreport.meta.domain.DataSource;
import com.easytoolsoft.easyreport.meta.domain.example.DataSourceExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 报表数据源(_rpt_datasource表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("DataSourceRepository")
public interface DataSourceRepository extends CrudRepository<DataSource, DataSourceExample, Integer> {
}
