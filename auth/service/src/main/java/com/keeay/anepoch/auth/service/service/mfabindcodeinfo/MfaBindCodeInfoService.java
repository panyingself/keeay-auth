package com.keeay.anepoch.auth.service.service.mfabindcodeinfo;

import com.keeay.anepoch.auth.service.model.*;
import com.keeay.anepoch.auth.service.service.BaseService;

/**
 * @author AI Admin
 */
public interface MfaBindCodeInfoService extends BaseService<MfaBindCodeInfo, Long> {

    /**
     * 修改用户使用标识为已使用
     *
     * @param appCode  appCode
     * @param appName  appName
     * @param userCode userCode
     * @return count
     */
    Integer updateUserUseFlagUsed(String appCode, String appName, String userCode);
}
