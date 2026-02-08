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
package org.springblade.rabbit.publisher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.rabbit.publisher.config.RabbitPublisherConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Rabbit 消息生产者服务
 *
 * @author Chill
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMessageService {

	private final RabbitTemplate rabbitTemplate;

	/**
	 * 发送示例消息
	 *
	 * @param routingKey 路由键
	 * @param message    消息内容
	 */
	public void sendDemoMessage(String routingKey, Object message) {
		try {
			rabbitTemplate.convertAndSend(RabbitPublisherConfiguration.EXCHANGE_NAME, routingKey, message);
			log.info("发送示例消息成功: routingKey={}, message={}", routingKey, message);
		} catch (Exception e) {
			log.error("发送示例消息失败: routingKey={}, message={}", routingKey, message, e);
		}
	}

	/**
	 * 发送普通消息
	 *
	 * @param routingKey 路由键
	 * @param message    消息内容
	 */
	public void sendMessage(String routingKey, Object message) {
		try {
			rabbitTemplate.convertAndSend(RabbitPublisherConfiguration.EXCHANGE_NAME, routingKey, message);
			log.info("发送消息成功: routingKey={}, message={}", routingKey, message);
		} catch (Exception e) {
			log.error("发送消息失败: routingKey={}, message={}", routingKey, message, e);
		}
	}

	/**
	 * 发送字节数组消息
	 *
	 * @param routingKey 路由键
	 * @param payload    消息载荷
	 */
	public void sendPayload(String routingKey, byte[] payload) {
		try {
			rabbitTemplate.convertAndSend(RabbitPublisherConfiguration.EXCHANGE_NAME, routingKey, payload);
			log.info("发送载荷消息成功: routingKey={}, payloadSize={}", routingKey, payload.length);
		} catch (Exception e) {
			log.error("发送载荷消息失败: routingKey={}, payloadSize={}", routingKey, payload.length, e);
		}
	}
}
