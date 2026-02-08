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
package org.springblade.mqtt.broker.listener;

import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.spring.server.event.MqttClientOfflineEvent;
import net.dreamlu.iot.mqtt.spring.server.event.MqttClientOnlineEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * 客户端上下线监听器
 *
 * @author Chill
 */
@Slf4j
@Service
public class MqttConnectStatusListener {

	@EventListener
	public void online(MqttClientOnlineEvent event) {
		log.info("MQTT客户端上线: {}", event);

		// 客户端上线处理逻辑
		String clientId = event.getClientId();

		log.info("客户端上线 - clientId: {}", clientId);

		// 可以在这里添加上线处理逻辑，例如：
		// 1. 记录上线日志
		// 2. 更新设备状态
		// 3. 发送上线通知
		// 4. 统计在线数量等

		handleClientOnline(clientId);
	}

	@EventListener
	public void offline(MqttClientOfflineEvent event) {
		log.info("MQTT客户端下线: {}", event);

		// 客户端下线处理逻辑
		String clientId = event.getClientId();

		log.info("客户端下线 - clientId: {}", clientId);

		// 可以在这里添加下线处理逻辑，例如：
		// 1. 记录下线日志
		// 2. 更新设备状态
		// 3. 发送下线通知
		// 4. 清理资源等

		handleClientOffline(clientId);
	}

	/**
	 * 处理客户端上线
	 *
	 * @param clientId 客户端ID
	 */
	private void handleClientOnline(String clientId) {
		try {
			// 添加具体的上线处理逻辑
			log.debug("执行客户端上线处理: clientId={}", clientId);
		} catch (Exception e) {
			log.error("处理客户端上线异常: clientId={}", clientId, e);
		}
	}

	/**
	 * 处理客户端下线
	 *
	 * @param clientId 客户端ID
	 */
	private void handleClientOffline(String clientId) {
		try {
			// 添加具体的下线处理逻辑
			log.debug("执行客户端下线处理: clientId={}", clientId);
		} catch (Exception e) {
			log.error("处理客户端下线异常: clientId={}", clientId, e);
		}
	}

}
