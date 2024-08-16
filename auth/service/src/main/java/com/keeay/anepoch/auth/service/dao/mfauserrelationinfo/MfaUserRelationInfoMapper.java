package com.keeay.anepoch.auth.service.dao.mfauserrelationinfo;

import com.keeay.anepoch.auth.service.dao.BaseMapper;
import com.keeay.anepoch.auth.service.model.MfaUserRelationInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author AI Admin
 */
@Repository
public interface MfaUserRelationInfoMapper extends BaseMapper<MfaUserRelationInfo> {

    /**
     * get one
     *
     * @param appCode  appCode
     * @param userCode userCode
     * @return one
     */
    MfaUserRelationInfo getOne(@Param("appCode") String appCode, @Param("userCode") String userCode);

    /**
     * 根据用户编码删除数据
     *
     * @param userCode userCode
     * @return success true orElse false
     */
    Integer deleteByUserCode(@Param("userCode") String userCode);
}
