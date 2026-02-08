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
import net.dreamlu.iot.mqtt.codec.MqttPublishMessage;
import net.dreamlu.iot.mqtt.codec.MqttQoS;
import net.dreamlu.iot.mqtt.core.server.event.IMqttMessageListener;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.utils.buffer.ByteBufferUtil;

/**
 * 服务端接收消息监听器
 *
 * @author Chill
 */
@Slf4j
@Service
public class MqttServerMessageListener implements IMqttMessageListener {

	@Override
	public void onMessage(ChannelContext context, String clientId, String topic, MqttQoS qoS, MqttPublishMessage message) {
		log.info("收到MQTT消息 - clientId: {}, topic: {}, qoS: {}, payload: {}",
			clientId, topic, qoS, ByteBufferUtil.toString(message.getPayload()));

		// 自定义数据流转逻辑
		// 可以在这里添加消息处理逻辑，例如：
		// 1. 数据解析和格式化
		// 2. 业务规则处理
		// 3. 数据存储
		// 4. 消息转发
		// 5. 告警处理等

		handleMessage(clientId, topic, message);
	}

	/**
	 * 处理接收到的消息
	 *
	 * @param clientId 客户端ID
	 * @param topic    主题
	 * @param message  消息
	 */
	private void handleMessage(String clientId, String topic, MqttPublishMessage message) {
		try {
			String payload = ByteBufferUtil.toString(message.getPayload());

			// 根据主题进行不同的处理
			if (topic.contains("/property/")) {
				log.info("处理属性消息: {}", payload);
				// 处理设备属性上报
			} else if (topic.contains("/event/")) {
				log.info("处理事件消息: {}", payload);
				// 处理设备事件上报
			} else if (topic.contains("/command/")) {
				log.info("处理命令消息: {}", payload);
				// 处理设备命令响应
			} else {
				log.info("处理通用消息: {}", payload);
				// 处理其他类型消息
			}

		} catch (Exception e) {
			log.error("处理MQTT消息异常: clientId={}, topic={}", clientId, topic, e);
		}
	}
}
