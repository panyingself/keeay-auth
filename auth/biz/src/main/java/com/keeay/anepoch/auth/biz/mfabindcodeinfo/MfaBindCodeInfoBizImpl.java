
package com.keeay.anepoch.auth.biz.mfabindcodeinfo;

import com.keeay.anepoch.auth.api.context.LoginUser;
import com.keeay.anepoch.auth.api.context.UserContext;
import com.keeay.anepoch.auth.biz.mfa.MfaBiz;
import com.keeay.anepoch.base.commons.utils.ConditionUtils;
import com.keeay.anepoch.auth.service.model.*;
import com.keeay.anepoch.auth.biz.mfabindcodeinfo.bo.*;
import com.keeay.anepoch.auth.service.service.mfabindcodeinfo.MfaBindCodeInfoService;
import com.keeay.anepoch.auth.biz.mfabindcodeinfo.converter.MfaBindCodeInfoBoConverter;
import com.keeay.anepoch.base.commons.monitor.BaseBizTemplate;
import org.apache.commons.collections4.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author AI Admin
 */
@Service
@Slf4j
public class MfaBindCodeInfoBizImpl implements MfaBindCodeInfoBiz {
    @Resource
    private MfaBindCodeInfoService mfaBindCodeInfoService;
    @Resource
    private MfaBiz mfaBiz;

    /**
     * 生成mfa绑定码
     *
     * @param addMfaBindCodeInfoBo addMfaBindCodeInfoBo
     * @return code
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateCode(MfaBindCodeInfoBo addMfaBindCodeInfoBo) {
        log.info("generateCode biz start , addMfaBindCodeInfoBo : {}", addMfaBindCodeInfoBo);
        return new BaseBizTemplate<String>() {
            @Override
            protected void checkParam() {
                ConditionUtils.checkArgument(StringUtils.isNotBlank(addMfaBindCodeInfoBo.getUserCode()), "userCode is blank");
            }

            @Override
            protected String process() {
                LoginUser loginUser = UserContext.getUser();
                if (StringUtils.isBlank(addMfaBindCodeInfoBo.getAppCode())) {
                    addMfaBindCodeInfoBo.setAppCode("system001");
                    addMfaBindCodeInfoBo.setAppName("管理中台");
                }
                // 生成新的绑定码,以前的MFA设备失效
                mfaBiz.removeUserMfa(loginUser.getUserCode());
                // 生成新的绑定码，以前的绑定码全部失效
                // 修改系统用户下的useFlag为已使用
                mfaBindCodeInfoService.updateUserUseFlagUsed(addMfaBindCodeInfoBo.getAppCode(), addMfaBindCodeInfoBo.getAppName(), addMfaBindCodeInfoBo.getUserCode());
                // 生成新的绑定码
                String checkCode = this.generateCode();
                addMfaBindCodeInfoBo.setCheckCode(checkCode);
                addMfaBindCodeInfoBo.setCreateUser(loginUser.getUserName());
                MfaBindCodeInfo waitToDb = MfaBindCodeInfoBoConverter.convertToMfaBindCodeInfo(addMfaBindCodeInfoBo);
                mfaBindCodeInfoService.insert(waitToDb);
                return checkCode;
            }

            private String generateCode() {
                return "123456";
            }
        }.execute();
    }

    /**
     * 校验验证码
     *
     * @param addMfaBindCodeInfoBo addMfaBindCodeInfoBo
     * @return success true orElse false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean verifyCode(MfaBindCodeInfoBo addMfaBindCodeInfoBo) {
        log.info("generateCode biz start , addMfaBindCodeInfoBo : {}", addMfaBindCodeInfoBo);
        return new BaseBizTemplate<Boolean>() {
            @Override
            protected Boolean process() {
                // 上下文获取用户编码
                LoginUser loginUser = UserContext.getUser();
                addMfaBindCodeInfoBo.setUserCode(loginUser.getUserCode());
                // default system
                if (StringUtils.isBlank(addMfaBindCodeInfoBo.getAppCode())) {
                    addMfaBindCodeInfoBo.setAppCode("system001");
                    addMfaBindCodeInfoBo.setAppName("管理中台");
                }
                MfaBindCodeInfo query = new MfaBindCodeInfo();
                query.setUserCode(addMfaBindCodeInfoBo.getUserCode());
                query.setAppCode(addMfaBindCodeInfoBo.getAppCode());
                query.setAppName(query.getAppName());
                query.setUseFlag(NumberUtils.INTEGER_ZERO);
                List<MfaBindCodeInfo> listFromDb = mfaBindCodeInfoService.list(query);
                if (CollectionUtils.isEmpty(listFromDb)) {
                    log.warn("generateCode biz fast end , listFromDb is empty , userCode : {}", addMfaBindCodeInfoBo.getUserCode());
                    return false;
                }
                //修改系统用户下的useFlag为已使用
                mfaBindCodeInfoService.updateUserUseFlagUsed(addMfaBindCodeInfoBo.getAppCode(), addMfaBindCodeInfoBo.getAppName(), addMfaBindCodeInfoBo.getUserCode());
                return true;
            }
        }.execute();
    }
}

