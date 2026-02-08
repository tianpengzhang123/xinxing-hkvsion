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
package org.springblade.mqtt.broker.service;

import org.tio.core.ChannelContext;

/**
 * MQTT 认证服务
 *
 * @author Chill
 */
public interface IMqttAuthService {

	/**
	 * 设备连接认证
	 *
	 * @param context      ChannelContext
	 * @param clientId     clientId
	 * @param userName     userName
	 * @param password     password
	 * @param clientNodeIp clientNodeIp
	 * @return 是否成功
	 */
	boolean hasAuth(ChannelContext context, String clientId, String userName, String password, String clientNodeIp);

}
