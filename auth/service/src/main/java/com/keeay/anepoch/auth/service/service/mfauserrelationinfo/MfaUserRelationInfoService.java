package com.keeay.anepoch.auth.service.service.mfauserrelationinfo;

import com.keeay.anepoch.auth.service.model.MfaUserRelationInfo;
import com.keeay.anepoch.auth.service.service.BaseService;

/**
 * @author AI Admin
 */
public interface MfaUserRelationInfoService extends BaseService<MfaUserRelationInfo, Long> {
    /**
     * get one
     *
     * @param appCode  appCode
     * @param userCode userCode
     * @return one
     */
    MfaUserRelationInfo getOne(String appCode, String userCode);

    /**
     * 根据用户编码删除数据
     *
     * @param userCode userCode
     * @return success true orElse false
     */
    Boolean deleteByUserCode(String userCode);
}
