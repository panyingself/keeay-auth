
package com.keeay.anepoch.auth.biz.mfabindcodeinfo;

import com.keeay.anepoch.auth.biz.mfabindcodeinfo.bo.*;

import java.util.List;


/**
 * @author AI Admin
 */
public interface MfaBindCodeInfoBiz {
    /**
     * 生成mfa绑定码
     *
     * @param addMfaBindCodeInfoBo addMfaBindCodeInfoBo
     * @return code
     */
    String generateCode(MfaBindCodeInfoBo addMfaBindCodeInfoBo);

    /**
     * 校验验证码
     *
     * @param addMfaBindCodeInfoBo addMfaBindCodeInfoBo
     * @return success true orElse false
     */
    Boolean verifyCode(MfaBindCodeInfoBo addMfaBindCodeInfoBo);
}

