package com.keeay.anepoch.auth.service.service.mfabindcodeinfo;

import com.keeay.anepoch.auth.service.dao.mfabindcodeinfo.MfaBindCodeInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.keeay.anepoch.auth.service.service.BaseServiceImpl;
import com.keeay.anepoch.auth.service.model.*;

import javax.annotation.Resource;

/**
 * @author AI Admin
 */
@Service
public class MfaBindCodeInfoServiceImpl extends BaseServiceImpl<MfaBindCodeInfo, Long> implements MfaBindCodeInfoService {
    @Resource
    private MfaBindCodeInfoMapper mfaBindCodeInfoMapper;

    /**
     * 修改用户使用标识为已使用
     *
     * @param appCode  appCode
     * @param appName  appName
     * @param userCode userCode
     * @return count
     */
    @Override
    public Integer updateUserUseFlagUsed(String appCode, String appName, String userCode) {
        if (StringUtils.isBlank(appCode)) {
            return 0;
        }
        if (StringUtils.isBlank(appName)) {
            return 0;
        }
        if (StringUtils.isBlank(userCode)) {
            return 0;
        }
        return mfaBindCodeInfoMapper.updateUserUseFlagUsed(appCode, appName, userCode);
    }
}
