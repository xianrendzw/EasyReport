package com.easytoolsoft.easyreport.mybatis.conf;

import javax.sql.DataSource;

import com.easytoolsoft.easyreport.mybatis.readwrite.DynamicDataSource;
import com.easytoolsoft.easyreport.mybatis.readwrite.DynamicDataSourcePlugin;
import com.easytoolsoft.easyreport.mybatis.readwrite.DynamicDataSourceTransactionManager;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 读写分离数据源配置类
 *
 * @or Tom Deng
 * @date 2017-03-28
 **/

@Configuration
@MapperScan(basePackages = ReadWriteDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "readwriteSqlSessionFactory")
public class ReadWriteDataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "com.easytoolsoft.easyreport.mybatis.sample.repository";
    static final String MAPPER_LOCATION = "classpath*:mybatis/UserMapper.xml";

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setScriptEncoding("UTF-8")
            .ignoreFailedDrops(true)
            .setName("mybatis_sample_master")
            .addScript("sql/schema.sql")
            .addScripts("sql/data.sql")
            .build();
    }

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setScriptEncoding("UTF-8")
            .ignoreFailedDrops(true)
            .setName("mybatis_sample_slave")
            .addScript("sql/schema.sql")
            .addScripts("sql/data.sql")
            .build();
    }

    @Bean(name = "readwriteDataSource")
    public DataSource dataSource(@Qualifier("masterDataSource") final DataSource masterDataSource,
                                 @Qualifier("slaveDataSource") final DataSource slaveDataSource) {
        final DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setWriteDataSource(masterDataSource);
        dynamicDataSource.setReadDataSource(slaveDataSource);
        return dynamicDataSource;
    }

    @Bean(name = "readwriteTransactionManager")
    public DataSourceTransactionManager transactionManager(
        @Qualifier("readwriteDataSource") final DataSource dataSource) {
        return new DynamicDataSourceTransactionManager(dataSource);
    }

    @Bean(name = "readwriteSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("readwriteDataSource") final DataSource dataSource)
        throws Exception {
        final SqlSessionFactoryBean sessionFactory = this.createSqlSessionFactoryBean(dataSource, MAPPER_LOCATION);
        sessionFactory.setPlugins(new Interceptor[] {new DynamicDataSourcePlugin()});
        return sessionFactory.getObject();
    }

    @Bean(name = "readwriteSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("readwriteSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
        throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "readwriteTransactionTemplate")
    public TransactionTemplate transactionTemplate(
        @Qualifier("readwriteTransactionManager") final DataSourceTransactionManager transactionManager)
        throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }
}
