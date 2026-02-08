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
package org.springblade.mqtt.broker.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springblade.mqtt.broker.service.IMqttAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;

/**
 * MQTT 认证处理服务
 *
 * @author Chill
 */
@Slf4j
@Service
public class MqttAuthServiceImpl implements IMqttAuthService {

	/**
	 * MQTT 认证最大间隔时间，默认 5 分钟
	 */
	@Value("${mqtt.auth.timeSecondDiff:300}")
	private int timeSecondDiff;

	@Override
	public boolean hasAuth(ChannelContext context, String clientId, String userName, String password, String clientNodeIp) {
		log.info("MQTT 客户端认证: clientId={}, userName={}, clientNodeIp={}", clientId, userName, clientNodeIp);

		// 自定义账号认证逻辑
		// 这里可以根据实际需求添加具体的认证逻辑
		// 例如：查询数据库验证用户名密码、检查设备白名单等

		// 简单的demo认证逻辑：允许所有连接
		if (userName != null && password != null) {
			log.info("MQTT 客户端认证成功: clientId={}", clientId);
			return true;
		}

		log.warn("MQTT 客户端认证失败: clientId={}", clientId);
		return false;
	}

}
