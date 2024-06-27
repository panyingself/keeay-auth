package com.keeay.anepoch.auth.service.service.mfauserrelationinfo;

import com.keeay.anepoch.auth.service.dao.mfauserrelationinfo.MfaUserRelationInfoMapper;
import com.keeay.anepoch.auth.service.model.MfaUserRelationInfo;
import com.keeay.anepoch.auth.service.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author AI Admin
 */
@Service
public class MfaUserRelationInfoServiceImpl extends BaseServiceImpl<MfaUserRelationInfo, Long> implements MfaUserRelationInfoService {
    @Resource
    private MfaUserRelationInfoMapper mfaUserRelationInfoMapper;

    /**
     * get one
     * @param appCode  appCode
     * @param userCode userCode
     * @return one
     */
    @Override
    public MfaUserRelationInfo getOne(String appCode, String userCode) {
        return mfaUserRelationInfoMapper.getOne(appCode, userCode);
    }
}
