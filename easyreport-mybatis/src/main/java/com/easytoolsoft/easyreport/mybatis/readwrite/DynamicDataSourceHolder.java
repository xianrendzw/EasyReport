package com.easytoolsoft.easyreport.mybatis.readwrite;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
public final class DynamicDataSourceHolder {
    private static final ThreadLocal<DynamicDataSourceType> holder = new ThreadLocal<>();

    private DynamicDataSourceHolder() {
    }

    public static void putDataSource(final DynamicDataSourceType dataSource) {
        holder.set(dataSource);
    }

    public static DynamicDataSourceType getDataSource() {
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

}
