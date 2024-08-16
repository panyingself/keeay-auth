
package com.keeay.anepoch.auth.web.controller;

import com.keeay.anepoch.auth.biz.mfabindcodeinfo.MfaBindCodeInfoBiz;
import com.keeay.anepoch.auth.biz.mfabindcodeinfo.bo.MfaBindCodeInfoBo;
import com.keeay.anepoch.auth.web.controller.mfabindcodeinfo.request.*;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author AI Admin
 */
@RestController
@RequestMapping("api/mfa/bindCode/info")
public class MfaBindCodeInfoController {
    @Resource
    private MfaBindCodeInfoBiz mfaBindCodeInfoBiz;


    /**
     * 生成绑定码
     *
     * @param addMfaBindCodeInfoRequest addMfaBindCodeInfoRequest
     * @return success true orElse false
     */
    @PostMapping("generateCode")
    public HttpResult<String> add(@RequestBody MfaBindCodeInfoAddRequest addMfaBindCodeInfoRequest) {
        MfaBindCodeInfoBo addMfaBindCodeInfoBo = JsonMoreUtils.toBean(JsonMoreUtils.toJson(addMfaBindCodeInfoRequest), MfaBindCodeInfoBo.class);
        return HttpResult.success(mfaBindCodeInfoBiz.generateCode(addMfaBindCodeInfoBo));
    }


    /**
     * 生成绑定码
     *
     * @param addMfaBindCodeInfoRequest addMfaBindCodeInfoRequest
     * @return success true orElse false
     */
    @PostMapping("verifyCode")
    public HttpResult<Boolean> verifyCode(@RequestBody MfaBindCodeInfoAddRequest addMfaBindCodeInfoRequest) {
        MfaBindCodeInfoBo addMfaBindCodeInfoBo = JsonMoreUtils.toBean(JsonMoreUtils.toJson(addMfaBindCodeInfoRequest), MfaBindCodeInfoBo.class);
        return HttpResult.success(mfaBindCodeInfoBiz.verifyCode(addMfaBindCodeInfoBo));
    }
}

