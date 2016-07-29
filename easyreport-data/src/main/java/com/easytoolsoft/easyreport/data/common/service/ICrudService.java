package com.easytoolsoft.easyreport.data.common.service;

/**
 * 基本增删改查(CRUD)数据访问服务接口
 *
 * @param <T>
 */
public interface ICrudService<T> extends
        IAddService<T>,
        IRemoveService<T>,
        IEditService<T>,
        IGetService<T> {
}
