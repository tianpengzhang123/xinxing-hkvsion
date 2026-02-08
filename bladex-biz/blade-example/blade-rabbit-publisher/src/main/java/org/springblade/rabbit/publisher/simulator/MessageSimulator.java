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
package org.springblade.rabbit.publisher.simulator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.rabbit.publisher.service.RabbitMessageService;
import org.springblade.rabbit.publisher.support.MessageData;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Rabbit 消息模拟器（模拟设备消息的转发）
 *
 * @author Chill
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSimulator implements CommandLineRunner {

	private final RabbitMessageService rabbitMessageService;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

	@Override
	public void run(String... args) {
		log.info("启动 Rabbit 消息生产者模拟器");

		// 启动定时任务
		startScheduledTasks();
	}

	/**
	 * 启动定时任务
	 */
	private void startScheduledTasks() {
		// 定期发送示例消息（每30秒）
		scheduler.scheduleAtFixedRate(this::sendDemoMessage, 10, 30, TimeUnit.SECONDS);

		// 定期发送数据消息（每60秒）
		scheduler.scheduleAtFixedRate(this::sendDataMessage, 15, 60, TimeUnit.SECONDS);

		// 定期发送通知消息（每120秒）
		scheduler.scheduleAtFixedRate(this::sendNotifyMessage, 20, 120, TimeUnit.SECONDS);

		// 定期发送事件消息（每180秒）
		scheduler.scheduleAtFixedRate(this::sendEventMessage, 25, 180, TimeUnit.SECONDS);

		log.info("已启动消息生产者定时任务");
	}

	/**
	 * 发送示例消息
	 */
	private void sendDemoMessage() {
		try {
			MessageData messageData = new MessageData();
			messageData.setId(UUID.randomUUID().toString().replace("-", ""));
			messageData.setTimestamp(System.currentTimeMillis());
			messageData.setType("demo");

			Map<String, Object> data = new HashMap<>();
			data.put("temperature", 20 + Math.random() * 10);
			data.put("humidity", 40 + Math.random() * 30);
			data.put("status", Math.random() > 0.5 ? "online" : "offline");
			messageData.setData(data);

			String routingKey = "demo.test." + System.currentTimeMillis();
			rabbitMessageService.sendDemoMessage(routingKey, JsonUtil.toJson(messageData));

			log.debug("已发送示例消息: routingKey={}", routingKey);
		} catch (Exception e) {
			log.error("发送示例消息失败", e);
		}
	}

	/**
	 * 发送数据消息
	 */
	private void sendDataMessage() {
		try {
			MessageData messageData = new MessageData();
			messageData.setId(UUID.randomUUID().toString().replace("-", ""));
			messageData.setTimestamp(System.currentTimeMillis());
			messageData.setType("data");

			Map<String, Object> data = new HashMap<>();
			data.put("deviceId", "device_" + (int)(Math.random() * 100));
			data.put("value", Math.random() * 100);
			data.put("unit", "celsius");
			messageData.setData(data);

			String routingKey = "demo.data." + System.currentTimeMillis();
			rabbitMessageService.sendDemoMessage(routingKey, JsonUtil.toJson(messageData));

			log.debug("已发送数据消息: routingKey={}", routingKey);
		} catch (Exception e) {
			log.error("发送数据消息失败", e);
		}
	}

	/**
	 * 发送通知消息
	 */
	private void sendNotifyMessage() {
		try {
			MessageData messageData = new MessageData();
			messageData.setId(UUID.randomUUID().toString().replace("-", ""));
			messageData.setTimestamp(System.currentTimeMillis());
			messageData.setType("notify");

			Map<String, Object> data = new HashMap<>();
			data.put("title", "系统通知");
			data.put("content", "这是一条通知消息");
			data.put("level", Math.random() > 0.5 ? "info" : "warning");
			messageData.setData(data);

			String routingKey = "message.notify." + System.currentTimeMillis();
			rabbitMessageService.sendMessage(routingKey, JsonUtil.toJson(messageData));

			log.debug("已发送通知消息: routingKey={}", routingKey);
		} catch (Exception e) {
			log.error("发送通知消息失败", e);
		}
	}

	/**
	 * 发送事件消息
	 */
	private void sendEventMessage() {
		try {
			MessageData messageData = new MessageData();
			messageData.setId(UUID.randomUUID().toString().replace("-", ""));
			messageData.setTimestamp(System.currentTimeMillis());
			messageData.setType("event");

			Map<String, Object> data = new HashMap<>();
			data.put("eventType", "user_action");
			data.put("action", Math.random() > 0.5 ? "login" : "logout");
			data.put("userId", "user_" + (int)(Math.random() * 1000));
			messageData.setData(data);

			String routingKey = "message.event." + System.currentTimeMillis();
			rabbitMessageService.sendMessage(routingKey, JsonUtil.toJson(messageData));

			log.debug("已发送事件消息: routingKey={}", routingKey);
		} catch (Exception e) {
			log.error("发送事件消息失败", e);
		}
	}

	/**
	 * 停止模拟器
	 */
	public void stop() {
		if (!scheduler.isShutdown()) {
			scheduler.shutdown();
			log.info("消息生产者模拟器已停止");
		}
	}
}
