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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 扩展功能
 *
 * @author Chill
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysBean implements Serializable {

	/**
	 * 不响应 ack
	 */
	public static final int ACK_NO = 0;
	/**
	 * 响应 ack
	 */
	public static final int ACK_NEED = 1;

	public SysBean(boolean ackNeed) {
		this(ackNeed ? ACK_NEED : ACK_NO);
	}

	/**
	 * 扩展功能字段，表示是否返回响应数据。0：不返回响应数据1：返回响应数据
	 */
	private int ack;

}
