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
package org.springblade.rabbit.publisher.support;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Rabbit 消息数据
 *
 * @author Chill
 */
@Data
public class MessageData implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 消息ID
	 */
	private String id;

	/**
	 * 消息类型
	 */
	private String type;

	/**
	 * 时间戳
	 */
	private Long timestamp;

	/**
	 * 消息数据
	 */
	private Object data;

	/**
	 * 消息版本
	 */
	private String version = "1.0";

}
