package com.keeay.anepoch.auth.service.model;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author AI Admin
 */
@Data
public class MfaBindCodeInfo implements Serializable {
	/** 主键id */
	private Long id;
	/** 应用code */
	private String appCode;
	/** 应用名称 */
	private String appName;
	/** 用户code */
	private String userCode;
	/** 校验码 */
	private String checkCode;
	/** 使用标识，默认未使用 0 - 1 */
	private Integer useFlag;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 创建人 */
	private String createUser;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}

