package com.easytoolsoft.easyreport.mybatis.readwrite;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Slf4j
@Intercepts({
    @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}),
    @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class DynamicDataSourcePlugin implements Interceptor {
    private static final Map<String, DynamicDataSourceType> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(final Invocation invocation) throws Throwable {
        final boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        if (!synchronizationActive) {
            final Object[] objects = invocation.getArgs();
            final MappedStatement mappedStatement = (MappedStatement)objects[0];
            final DynamicDataSourceType dynamicDataSourceType = this.getDynamicDataSourceType(mappedStatement);
            cacheMap.put(mappedStatement.getId(), dynamicDataSourceType);
            DynamicDataSourceHolder.putDataSource(dynamicDataSourceType);

            log.info("SQL [{}] from datasource [{}],SqlCommandType [{}]",
                mappedStatement.getId(), dynamicDataSourceType.name(), mappedStatement.getSqlCommandType().name());
        }
        return invocation.proceed();
    }

    private DynamicDataSourceType getDynamicDataSourceType(final MappedStatement mappedStatement) {
        final DynamicDataSourceType dynamicDataSourceType = cacheMap.get(mappedStatement.getId());
        if (dynamicDataSourceType != null) {
            return dynamicDataSourceType;
        }
        //如是不是select语句则使用主库
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return DynamicDataSourceType.WRITE;
        }
        //selectKey为自增id查询主键(SELECT LAST_INSERT_ID())方法,使用主库
        if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
            return DynamicDataSourceType.WRITE;
        }
        return DynamicDataSourceType.READ;
    }

    @Override
    public Object plugin(final Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(final Properties properties) {
    }
}
