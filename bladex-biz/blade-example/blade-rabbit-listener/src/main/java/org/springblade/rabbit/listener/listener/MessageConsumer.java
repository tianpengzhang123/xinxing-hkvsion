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
package org.springblade.rabbit.listener.listener;

import lombok.extern.slf4j.Slf4j;
import org.springblade.rabbit.listener.config.RabbitListenerConfiguration;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Rabbit 消息消费者
 *
 * @author Chill
 */
@Slf4j
@Component
public class MessageConsumer {

	/**
	 * 监听示例队列消息
	 *
	 * @param message 消息
	 */
	@RabbitListener(queues = RabbitListenerConfiguration.DEMO_QUEUE_NAME)
	public void receiveDemoMessage(Message message) {
		String routingKey = message.getMessageProperties().getReceivedRoutingKey();
		String payload = new String(message.getBody());
		log.info("收到示例消息: routingKey={}, payload={}", routingKey, payload);

		// 处理示例消息的业务逻辑
		processDemoMessage(routingKey, payload);
	}

	/**
	 * 监听普通消息队列消息
	 *
	 * @param message 消息
	 */
	@RabbitListener(queues = RabbitListenerConfiguration.MESSAGE_QUEUE_NAME)
	public void receiveMessage(Message message) {
		String routingKey = message.getMessageProperties().getReceivedRoutingKey();
		String payload = new String(message.getBody());
		log.info("收到普通消息: routingKey={}, payload={}", routingKey, payload);

		// 处理普通消息的业务逻辑
		processMessage(routingKey, payload);
	}

	/**
	 * 处理示例消息
	 *
	 * @param routingKey 路由键
	 * @param payload    消息载荷
	 */
	private void processDemoMessage(String routingKey, String payload) {
		try {
			// 这里可以添加具体的业务处理逻辑
			log.debug("处理示例消息: routingKey={}, payload={}", routingKey, payload);

			// 示例: 如果是特定的路由键，执行特定操作
			if (routingKey.startsWith("demo.test")) {
				log.info("执行测试操作: {}", payload);
			} else if (routingKey.startsWith("demo.data")) {
				log.info("处理数据: {}", payload);
			}
		} catch (Exception e) {
			log.error("处理示例消息失败: routingKey={}, payload={}", routingKey, payload, e);
		}
	}

	/**
	 * 处理普通消息
	 *
	 * @param routingKey 路由键
	 * @param payload    消息载荷
	 */
	private void processMessage(String routingKey, String payload) {
		try {
			// 这里可以添加具体的业务处理逻辑
			log.debug("处理普通消息: routingKey={}, payload={}", routingKey, payload);

			// 示例: 根据路由键进行不同的处理
			if (routingKey.startsWith("message.notify")) {
				log.info("处理通知消息: {}", payload);
			} else if (routingKey.startsWith("message.event")) {
				log.info("处理事件消息: {}", payload);
			}
		} catch (Exception e) {
			log.error("处理普通消息失败: routingKey={}, payload={}", routingKey, payload, e);
		}
	}
}
