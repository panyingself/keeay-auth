package com.keeay.anepoch.auth.biz.mfa;

import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import com.keeay.anepoch.auth.biz.auth.helper.AuthHelper;
import com.keeay.anepoch.auth.service.model.MfaUserRelationInfo;
import com.keeay.anepoch.auth.service.service.mfauserrelationinfo.MfaUserRelationInfoService;
import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.monitor.BaseBizTemplate;
import de.taimos.totp.TOTP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/6/26 - 17:49
 */
@Service
@Slf4j
public class MfaBizImpl implements MfaBiz {
    private static final String ISSUER = "keeay-user";
    private static final String APP_CODE = "system001";
    private static final String APP_NAME = "管理中台";
    @Resource
    private MfaUserRelationInfoService mfaUserRelationInfoService;
    @Resource
    private AuthHelper authHelper;

    /**
     * 验证otpCode是否正确
     *
     * @param userCode    userCode
     * @param userOtpCode userOtpCode(用户实时验证码)
     * @return success true orElse false
     */
    @Override
    public Boolean verifyOptCode(String userCode, String userOtpCode) {
        log.info("verifyOptCode biz start, userCode : {} , userOtpCode : {}", userCode, userOtpCode);
        return new BaseBizTemplate<Boolean>() {
            @Override
            protected Boolean process() {
                //根据userCode查询绑定的otp secret key
                MfaUserRelationInfo fromDb = mfaUserRelationInfoService.getOne(APP_CODE, userCode);
                if (Objects.isNull(fromDb)) {
                    log.error("用户未曾绑定OPT信息,userCode : {}", userCode);
                    throw new BizException("验证码错误");
                }
                String generatedOtp = getTOTPCode(fromDb.getMfaSecret());
                //比较
                try {
                    //校验成功
                    if (generatedOtp.equals(userOtpCode)) {
                        //将用户设置为登录成功
                        TokenBo redisLoginUser = authHelper.getRedisLoginUser(userCode);
                        redisLoginUser.setMfaFlag(Boolean.TRUE);
                        authHelper.saveRedisLoginUser(redisLoginUser);
                        return true;
                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BizException("用户未前置登录");
                }
            }
        }.execute();
    }

    /**
     * 根据32位随机码获得正确的6位数字
     *
     * @param secretKey secretKey
     * @return number
     */
    private String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    /**
     * 生成mfa二维码url
     *
     * @param userCode userCode
     * @return url
     */
    @Override
    public String generateMfaUrl(String userCode) {
        log.info("generateMfaUrl biz start, userCode : {} ", userCode);
        return new BaseBizTemplate<String>() {
            @Override
            protected String process() {
                String qrCodeText = "";
                try {
                    //查询是否存在，存在就用已有的
                    MfaUserRelationInfo fromDb = mfaUserRelationInfoService.getOne(APP_CODE, userCode);
                    if (Objects.nonNull(fromDb)) {
                        return fromDb.getMfaText();
                    }
                    //不存在，重新生成
                    String secret = getSecretKey();
                    qrCodeText = String.format(
                            "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                            URLEncoder.encode(ISSUER, StandardCharsets.UTF_8.toString()),
                            URLEncoder.encode(userCode, StandardCharsets.UTF_8.toString()),
                            secret,
                            URLEncoder.encode(ISSUER, StandardCharsets.UTF_8.toString())
                    );
                    //将用户与url关系/secret关系保存至db
                    MfaUserRelationInfo waitToDb = new MfaUserRelationInfo();
                    waitToDb.setAppCode(APP_CODE);
                    waitToDb.setAppName(APP_NAME);
                    waitToDb.setUserCode(userCode);
                    waitToDb.setMfaSecret(secret);
                    waitToDb.setMfaText(qrCodeText);
                    mfaUserRelationInfoService.insert(waitToDb);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BizException("系统异常");
                }
                return qrCodeText;
            }
        }.execute();
    }

    private String getSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

}
