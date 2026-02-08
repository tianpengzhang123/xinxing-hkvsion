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
package org.springblade.mqtt.client.simulator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.core.client.MqttClient;
import org.springblade.mqtt.client.support.DataReq;
import org.springblade.mqtt.client.support.NtpReq;
import org.tio.utils.buffer.ByteBufferUtil;
import org.tio.utils.json.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 设备模拟器
 *
 * @author Chill
 */
@Slf4j
@RequiredArgsConstructor
public class DeviceSimulator {
	private final String productKey;
	private final String deviceName;
	private final String deviceSecret;
	private final String serverHost;
	private final int serverPort;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

	/**
	 * 启动模拟器
	 */
	public void start() {
		log.info("启动设备模拟器: productKey={}, deviceName={}", productKey, deviceName);

		// 初始化 MQTT 客户端
		String clientId = generateClientId();
		String username = generateUsername();
		String password = generatePassword();

		log.info("连接配置: clientId={}, username={}, host={}:{}", clientId, username, serverHost, serverPort);

		try {
			MqttClient client = MqttClient.create()
				.ip(serverHost)
				.port(serverPort)
				.username(username)
				.password(password)
				.clientId(clientId)
				.connectSync();

			log.info("MQTT客户端连接成功");

			// 订阅主题
			subscribeTopics(client);
			// 启动任务调度
			scheduleTasks(client);

		} catch (Exception e) {
			log.error("MQTT客户端连接失败", e);
		}
	}

	/**
	 * 生成客户端ID
	 *
	 * @return clientId
	 */
	private String generateClientId() {
		return productKey + "_" + deviceName + "_" + System.currentTimeMillis();
	}

	/**
	 * 生成用户名
	 *
	 * @return username
	 */
	private String generateUsername() {
		return deviceName + "&" + productKey;
	}

	/**
	 * 生成密码
	 *
	 * @return password
	 */
	private String generatePassword() {
		// 简化的密码生成逻辑
		return deviceSecret;
	}

	/**
	 * 订阅主题
	 *
	 * @param client MqttClient
	 */
	private void subscribeTopics(MqttClient client) {
		// 订阅设备相关主题
		String topicPrefix = "/blade/sys/" + productKey + "/" + deviceName;

		// 订阅属性设置
		client.subQos0(topicPrefix + "/thing/service/property/set", (context, topic, message, payload) -> {
			log.info("收到属性设置: topic={}, payload={}", topic, ByteBufferUtil.toString(payload));
		});

		// 订阅服务调用
		client.subQos0(topicPrefix + "/thing/service/+", (context, topic, message, payload) -> {
			log.info("收到服务调用: topic={}, payload={}", topic, ByteBufferUtil.toString(payload));
		});

		// 订阅NTP响应
		client.subQos0("/blade/ext/ntp/" + productKey + "/" + deviceName + "/response", (context, topic, message, payload) -> {
			log.info("收到NTP响应: topic={}, payload={}", topic, ByteBufferUtil.toString(payload));
		});

		log.info("已订阅设备主题: {}", topicPrefix);
	}

	/**
	 * 任务调度
	 *
	 * @param client MqttClient
	 */
	private void scheduleTasks(MqttClient client) {
		// 定期上报属性数据（每30秒）
		scheduler.scheduleAtFixedRate(() -> {
			publishPropertyData(client);
		}, 5, 30, TimeUnit.SECONDS);

		// 定期上报事件数据（每60秒）
		scheduler.scheduleAtFixedRate(() -> {
			publishEventData(client);
		}, 10, 60, TimeUnit.SECONDS);

		// 定期发送心跳数据（每120秒）
		scheduler.scheduleAtFixedRate(() -> {
			publishHeartbeat(client);
		}, 15, 120, TimeUnit.SECONDS);

		// 定期请求NTP时间（每300秒）
		scheduler.scheduleAtFixedRate(() -> {
			publishNtpRequest(client);
		}, 20, 300, TimeUnit.SECONDS);

		log.info("已启动定时任务调度");
	}

	/**
	 * 发布属性数据
	 *
	 * @param client MqttClient
	 */
	private void publishPropertyData(MqttClient client) {
		try {
			DataReq<Map<String, Object>> req = new DataReq<>();
			req.setId(UUID.randomUUID().toString().replace("-", ""));
			req.setVersion("1.0");

			Map<String, Object> params = new HashMap<>();
			params.put("temperature", 20 + Math.random() * 10); // 温度：20-30度
			params.put("humidity", 40 + Math.random() * 30);    // 湿度：40-70%
			params.put("lightSwitch", Math.random() > 0.5 ? 1 : 0); // 灯开关
			req.setParams(params);

			String topic = "/blade/sys/" + productKey + "/" + deviceName + "/thing/event/property/post";
			client.publish(topic, JsonUtil.toJsonBytes(req));

			log.debug("已发布属性数据: {}", JsonUtil.toJsonString(params));
		} catch (Exception e) {
			log.error("发布属性数据失败", e);
		}
	}

	/**
	 * 发布事件数据
	 *
	 * @param client MqttClient
	 */
	private void publishEventData(MqttClient client) {
		try {
			DataReq<Map<String, Object>> req = new DataReq<>();
			req.setId(UUID.randomUUID().toString().replace("-", ""));
			req.setVersion("1.0");

			Map<String, Object> params = new HashMap<>();
			Map<String, Object> output = new HashMap<>();
			output.put("eventLevel", "INFO");
			output.put("eventMessage", "设备运行正常");
			output.put("timestamp", System.currentTimeMillis());

			params.put("output", JsonUtil.toJsonString(output));
			params.put("eventName", "状态事件");
			params.put("eventType", "info");
			req.setParams(params);

			String topic = "/blade/sys/" + productKey + "/" + deviceName + "/thing/event/StatusEvent/post";
			client.publish(topic, JsonUtil.toJsonBytes(req));

			log.debug("已发布事件数据");
		} catch (Exception e) {
			log.error("发布事件数据失败", e);
		}
	}

	/**
	 * 发布心跳数据
	 *
	 * @param client MqttClient
	 */
	private void publishHeartbeat(MqttClient client) {
		try {
			Map<String, Object> heartbeat = new HashMap<>();
			heartbeat.put("deviceId", deviceName);
			heartbeat.put("productKey", productKey);
			heartbeat.put("timestamp", System.currentTimeMillis());
			heartbeat.put("status", "online");

			String topic = "/blade/sys/" + productKey + "/" + deviceName + "/heartbeat";
			client.publish(topic, JsonUtil.toJsonBytes(heartbeat));

			log.debug("已发布心跳数据");
		} catch (Exception e) {
			log.error("发布心跳数据失败", e);
		}
	}

	/**
	 * 发布NTP请求
	 *
	 * @param client MqttClient
	 */
	private void publishNtpRequest(MqttClient client) {
		try {
			NtpReq req = new NtpReq();
			req.setDeviceSendTime(String.valueOf(System.currentTimeMillis()));

			String topic = "/blade/ext/ntp/" + productKey + "/" + deviceName + "/request";
			client.publish(topic, JsonUtil.toJsonBytes(req));

			log.debug("已发布NTP请求");
		} catch (Exception e) {
			log.error("发布NTP请求失败", e);
		}
	}

	/**
	 * 停止模拟器
	 */
	public void stop() {
		scheduler.shutdown();
		log.info("设备模拟器已停止");
	}
}
