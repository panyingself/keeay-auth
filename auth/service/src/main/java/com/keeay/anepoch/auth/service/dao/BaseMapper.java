/*
 * Copyright (c) 2018. panying
 */

package com.keeay.anepoch.auth.service.dao;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author pany
 */
public interface BaseMapper<T> {
    /**
     * 插入对象
     *
     * @param var1
     * @return insert count
     */
    int insert(T var1);

    /**
     * 删除对象
     *
     * @param var1
     * @return delete count
     */
    int delete(Object var1);

    /**
     * 修改对象
     *
     * @param var1
     * @return update count
     */
    int update(T var1);

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
    T getDetailById(@RequestParam("id") Long id);
}