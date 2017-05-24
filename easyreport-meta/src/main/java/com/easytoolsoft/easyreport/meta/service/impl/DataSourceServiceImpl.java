package com.easytoolsoft.easyreport.meta.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.easytoolsoft.easyreport.meta.data.DataSourceRepository;
import com.easytoolsoft.easyreport.meta.domain.DataSource;
import com.easytoolsoft.easyreport.meta.domain.example.DataSourceExample;
import com.easytoolsoft.easyreport.meta.service.DataSourceService;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Slf4j
@Service("DataSourceService")
public class DataSourceServiceImpl
    extends AbstractCrudService<DataSourceRepository, DataSource, DataSourceExample, Integer>
    implements DataSourceService {

    @Override
    protected DataSourceExample getPageExample(final String fieldName, final String keyword) {
        final DataSourceExample example = new DataSourceExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * @param driverClass
     * @param url
     * @param user
     * @param password
     * @return
     */
    @Override
    public boolean testConnection(final String driverClass, final String url, final String user,
                                  final String password) {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (final Exception e) {
            log.error("testConnection", e);
            return false;
        } finally {
            this.releaseConnection(conn);
        }
    }

    private void releaseConnection(final Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (final SQLException ex) {
                log.error("测试数据库连接后释放资源失败", ex);
            }
        }
    }
}
