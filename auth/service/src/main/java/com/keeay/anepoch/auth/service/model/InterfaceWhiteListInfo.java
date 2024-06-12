package com.keeay.anepoch.auth.service.model;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author AI Admin
 */
@Data
public class InterfaceWhiteListInfo implements Serializable {
	/** 主键 */
	private Long id;
	/** 应用 */
	private String app;
	/** 服务 */
	private String server;
	/** 描述 */
	private String description;
	/** 备注 */
	private String remark;
	/** 忽略接口地址 */
	private String interfaceUrl;
	/** 创建人 */
	private String createUser;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 修改人 */
	private String updateUser;
	/** 更新时间 */
	private java.util.Date updateTime;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}

