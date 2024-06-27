package com.keeay.anepoch.auth.service.service.mfauserrelationinfo;

import com.keeay.anepoch.auth.service.model.MfaUserRelationInfo;
import com.keeay.anepoch.auth.service.service.BaseService;

/**
 * @author AI Admin
 */
public interface MfaUserRelationInfoService extends BaseService<MfaUserRelationInfo, Long> {
    /**
     * get one
     * @param appCode  appCode
     * @param userCode userCode
     * @return one
     */
    MfaUserRelationInfo getOne(String appCode, String userCode);
}
