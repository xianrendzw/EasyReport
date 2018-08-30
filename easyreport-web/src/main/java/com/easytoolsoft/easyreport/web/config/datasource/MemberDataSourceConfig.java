package com.easytoolsoft.easyreport.web.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 用户与权限业务数据源配置类
 *
 * @author Tom Deng
 **/
@Configuration
@MapperScan(basePackages = MemberDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "memberSqlSessionFactory")
public class MemberDataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "com.easytoolsoft.easyreport.membership.data";
    static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/membership/*.xml";

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "easytoolsoft.easyreport.member.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "memberDataSource")
    public DataSource dataSource() {
        DruidDataSource dsh = firstDataSourceProperties().initializeDataSourceBuilder().type
            (DruidDataSource.class).build();
        dsh.setValidationQuery("select 1");
        Resource initSchema = new ClassPathResource("schema.sql");
        Resource initData = new ClassPathResource("data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
        DatabasePopulatorUtils.execute(databasePopulator, dsh);
        return dsh;
    }

    @Primary
    @Bean(name = "memberTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("memberDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "memberSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("memberDataSource") final DataSource dataSource)
        throws Exception {
        return this.createSqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    @Primary
    @Bean(name = "memberSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("memberSqlSessionFactory") final
    SqlSessionFactory sqlSessionFactory)
        throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "memberTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("memberTransactionManager") final
    DataSourceTransactionManager transactionManager)
        throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }
}
