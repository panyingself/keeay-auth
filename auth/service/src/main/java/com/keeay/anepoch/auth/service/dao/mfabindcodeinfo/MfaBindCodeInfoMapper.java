package com.keeay.anepoch.auth.service.dao.mfabindcodeinfo;

import com.keeay.anepoch.auth.service.model.*;
import com.keeay.anepoch.auth.service.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author AI Admin
 */
@Repository
public interface MfaBindCodeInfoMapper extends BaseMapper<MfaBindCodeInfo> {
    /**
     * 修改用户使用标识为已使用
     *
     * @param appCode  appCode
     * @param appName  appName
     * @param userCode userCode
     * @return count
     */
    Integer updateUserUseFlagUsed(@Param("appCode") String appCode, @Param("appName") String appName, @Param("userCode") String userCode);
}
