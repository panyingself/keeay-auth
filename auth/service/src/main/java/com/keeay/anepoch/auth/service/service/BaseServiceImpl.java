
package com.keeay.anepoch.auth.service.service;

import com.keeay.anepoch.auth.service.dao.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author py
 * @date 2018/11/26 3:52 PM.
 */
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
    @Autowired
    private BaseMapper<T> baseMapper;

    public BaseServiceImpl() {
    }

    @Override
    public Boolean insert(T record) {
        return this.baseMapper.insert(record) > 0;
    }

    @Override
    public Boolean delete(T record) {
        return this.baseMapper.delete(record) > 0;
    }

    @Override
    public Boolean update(T record) {
        return this.baseMapper.update(record) > 0;
    }

    /**
     * 查询对象集合
     *
     * @param var1
     * @return result list
     */
    @Override
    public List<T> list(T var1) {
        return this.baseMapper.list(var1);
    }

    /**
     * 查询对象集合
     *
     * @param id id
     * @return result
     */
    @Override
    public T getDetailById(Long id) {
        return this.baseMapper.getDetailById(id);
    }
}