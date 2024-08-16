package com.keeay.anepoch.auth.service.service.mfauserrelationinfo;

import com.keeay.anepoch.auth.service.dao.mfauserrelationinfo.MfaUserRelationInfoMapper;
import com.keeay.anepoch.auth.service.model.MfaUserRelationInfo;
import com.keeay.anepoch.auth.service.service.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
     *
     * @param appCode  appCode
     * @param userCode userCode
     * @return one
     */
    @Override
    public MfaUserRelationInfo getOne(String appCode, String userCode) {
        return mfaUserRelationInfoMapper.getOne(appCode, userCode);
    }

    /**
     * 根据用户编码删除数据
     *
     * @param userCode userCode
     * @return success true orElse false
     */
    @Override
    public Boolean deleteByUserCode(String userCode) {
        if (StringUtils.isBlank(userCode)) {
            return false;
        }
        return mfaUserRelationInfoMapper.deleteByUserCode(userCode) > 0;
    }
}
