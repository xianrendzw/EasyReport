package com.easytoolsoft.easyreport.scheduler.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 报表业务数据源配置类
 *
 * @author Tom Deng
 * @date 2017-03-28
 **/
@Configuration
@MapperScan(basePackages = MetaDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "metaSqlSessionFactory")
public class MetaDataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "com.easytoolsoft.easyreport.report.data";
    static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/report/*.xml";

    @Value("${easytoolsoft.easyreport.report.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @ConfigurationProperties(prefix = "easytoolsoft.easyreport.report.datasource")
    @Bean(name = "metaDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .type(this.dataSourceType)
            .build();
    }

    @Bean(name = "metaTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("metaDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Bean(name = "metaSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("metaDataSource") final DataSource dataSource)
        throws Exception {
        return this.createSqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    @Bean(name = "metaSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("metaSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
        throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "metaTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("metaTransactionManager") final
                                                   DataSourceTransactionManager transactionManager)
        throws Exception {
        return new TransactionTemplate(transactionManager);
    }
}
