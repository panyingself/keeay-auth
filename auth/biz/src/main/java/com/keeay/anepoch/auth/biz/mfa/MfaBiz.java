package com.keeay.anepoch.auth.biz.mfa;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/6/26 - 17:49
 */
public interface MfaBiz {
    /**
     * 验证otpCode是否正确
     *
     * @param jwt         jwt
     * @param userOtpCode userOtpCode(用户实时验证码)
     * @return success true orElse false
     */
    Boolean verifyOptCode(String jwt, String userOtpCode);

    /**
     * 生成mfa二维码url
     *
     * @param jwt jwt
     * @return url
     */
    String generateMfaUrl(String jwt);

    /**
     * 删除用户mfa数据
     *
     * @param userCode userCode
     * @return success true orElse false
     */
    Boolean removeUserMfa(String userCode);
}
