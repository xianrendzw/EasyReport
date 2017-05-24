package com.easytoolsoft.easyreport.scheduler.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Tom Deng
 * @date 2017-05-19
 **/
public abstract class AbstractDataSourceConfig {
    protected static final String CONFIG_LOCATION = "classpath:mybatis/mybatis-config.xml";

    protected DataSourceTransactionManager createTransactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    protected SqlSessionFactory createSqlSessionFactory(final DataSource dataSource, final String mapperLocation)
        throws Exception {
        return this.createSqlSessionFactoryBean(dataSource, mapperLocation).getObject();
    }

    protected SqlSessionFactoryBean createSqlSessionFactoryBean(final DataSource dataSource,
                                                                final String mapperLocation) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CONFIG_LOCATION));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        return sessionFactory;
    }

    protected SqlSessionTemplate createSqlSessionTemplate(final SqlSessionFactory sqlSessionFactory)
        throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    protected TransactionTemplate createTransactionTemplate(final DataSourceTransactionManager transactionManager)
        throws Exception {
        return new TransactionTemplate(transactionManager);
    }
}
