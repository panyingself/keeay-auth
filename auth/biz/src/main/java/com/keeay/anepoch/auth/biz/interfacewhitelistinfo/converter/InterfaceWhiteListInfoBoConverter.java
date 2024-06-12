package com.keeay.anepoch.auth.biz.interfacewhitelistinfo.converter;

import com.keeay.anepoch.auth.service.model.*;
import com.keeay.anepoch.auth.biz.interfacewhitelistinfo.bo.*;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import java.util.Objects;
import lombok.Data;
/**
 * @author AI Admin
 */
@Data
public class InterfaceWhiteListInfoBoConverter {
	public static InterfaceWhiteListInfo convertToInterfaceWhiteListInfo(InterfaceWhiteListInfoBo targetBo) {
		if (Objects.isNull(targetBo)) {
			return null;
		}
		return JsonMoreUtils.toBean(JsonMoreUtils.toJson(targetBo),InterfaceWhiteListInfo.class);
		//或者构建你的实际逻辑
//		return InterfaceWhiteListInfo.builder()
////				//fit your code
////				.build();
	}
}


