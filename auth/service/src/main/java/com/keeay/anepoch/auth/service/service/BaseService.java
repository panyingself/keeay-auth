/*
 * Copyright (c) 2018. panying
 */

package com.keeay.anepoch.auth.service.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author py
 * @date 2018/11/26 3:35 PM.
 */
public interface BaseService<T, PK extends Serializable> {
    /**
     * 插入对象
     *
     * @param var1
     * @return insert count
     */
    Boolean insert(T var1);

    /**
     * 删除对象
     *
     * @param var1
     * @return delete count
     */
    Boolean delete(T var1);

    /**
     * 修改对象
     *
     * @param var1
     * @return update count
     */
    Boolean update(T var1);

    /**
     * 查询对象集合
     *
     * @param var1
     * @return result list
     */
    List<T> list(T var1);

    /**
     * 查询对象集合
     *
     * @param id id
     * @return result
     */
    T getDetailById(Long id);

}
