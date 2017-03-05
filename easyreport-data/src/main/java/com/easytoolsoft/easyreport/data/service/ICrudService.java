package com.easytoolsoft.easyreport.data.service;

/**
 * 基本增删改查(CRUD)数据访问服务接口
 *
 * @author tomdeng
 * @param <T>
 */
public interface ICrudService<T, U> extends
        IAddService<T>,
        IRemoveService<T, U>,
        IEditService<T, U>,
        IGetService<T, U> {
}
