package com.keeay.anepoch.auth.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.keeay.anepoch.auth.biz.mfa.MfaBiz;
import com.keeay.anepoch.auth.web.controller.request.MfaRequest;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/6/25 - 17:12
 */
@RestController
@RequestMapping("/api/mfa")
public class MfaController {
    @Resource
    private MfaBiz mfaBiz;
    private static final String appName = "keeay-user";
    public static final String SECRET_KEY = "UBKHKX2S34GIXGN6HF46C3D44YJF34Z3";
    private static final String userName = "keeay-user-name";


    /**
     * 第一步：开启FMA认证
     * 第二步：根据登录的用户名、密码，生成二维码（维护用户与secret关系,一个用户一个secret）
     * 第三步：用户使用设备绑定二维码，得到验证码
     * 第四步: 校验验证码：传入用户信息，验证码验证。
     *
     * @param response response
     * @throws WriterException WriterException
     * @throws IOException     IOException
     */
    @GetMapping("/generate-qr")
    public void generateQrCode(@RequestParam String jwt, HttpServletResponse response) throws WriterException, IOException {
        String qrCodeText = mfaBiz.generateMfaUrl(jwt);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 200, 200);

        response.setContentType("image/png");
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());
    }

    @PostMapping("/verify-otp")
    public HttpResult<Boolean> verifyOtp(@RequestBody MfaRequest mfaRequest) {
        Boolean result = mfaBiz.verifyOptCode(mfaRequest.getJwt(), mfaRequest.getOtpCode());
        if (result) {
            return HttpResult.success(true, "验证成功");
        }
        return HttpResult.success(false, "验证失败");
    }
}
