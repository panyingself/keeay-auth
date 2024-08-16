package com.keeay.anepoch.auth.biz.mfabindcodeinfo.converter;

import com.keeay.anepoch.auth.service.model.*;
import com.keeay.anepoch.auth.biz.mfabindcodeinfo.bo.*;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import java.util.Objects;
import lombok.Data;
/**
 * @author AI Admin
 */
@Data
public class MfaBindCodeInfoBoConverter {
	public static MfaBindCodeInfo convertToMfaBindCodeInfo(MfaBindCodeInfoBo targetBo) {
		if (Objects.isNull(targetBo)) {
			return null;
		}
		return JsonMoreUtils.toBean(JsonMoreUtils.toJson(targetBo),MfaBindCodeInfo.class);
		//或者构建你的实际逻辑
//		return MfaBindCodeInfo.builder()
////				//fit your code
////				.build();
	}
}


