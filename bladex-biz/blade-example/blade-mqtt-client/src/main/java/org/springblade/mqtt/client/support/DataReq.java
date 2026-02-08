/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.mqtt.client.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备通用数据格式
 *
 * @author Chill
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataReq<T> implements Serializable {

	/**
	 * 消息ID号。uuid，去掉短横线，32位，全局唯一，用于ack或系统消息追踪
	 */
	private String id;

	/**
	 * 协议版本号，目前协议版本号唯一取值为1.0
	 */
	private String version;

	/**
	 * 扩展功能的参数，其下包含各功能字段。平台可扩展，或可自行扩展，自行扩展的参数需在自定义解析模块自行解析
	 */
	private SysBean sys;

	/**
	 * 请求方法。
	 */
	private String method;

	/**
	 * 请求参数
	 */
	private T params;

}
