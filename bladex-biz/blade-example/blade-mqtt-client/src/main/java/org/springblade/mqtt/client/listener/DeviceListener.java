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
package org.springblade.mqtt.client.listener;

import lombok.extern.slf4j.Slf4j;
import org.springblade.mqtt.client.simulator.DeviceSimulator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 设备应用监听器
 *
 * @author Chill
 */
@Slf4j
@Component
public class DeviceListener {

	@Value("${mqtt.client.productKey:DEMO_PRODUCT}")
	private String productKey;

	@Value("${mqtt.client.deviceName:DEMO_DEVICE}")
	private String deviceName;

	@Value("${mqtt.client.deviceSecret:demo_secret_123}")
	private String deviceSecret;

	@Value("${mqtt.client.serverHost:localhost}")
	private String serverHost;

	@Value("${mqtt.client.serverPort:1883}")
	private int serverPort;

	@Value("${mqtt.client.enabled:true}")
	private boolean enabled;

	@EventListener(ApplicationReadyEvent.class)
	public void handleApplicationReady() {
		if (!enabled) {
			log.info("MQTT客户端已禁用，跳过启动");
			return;
		}

		log.info("应用启动完成，开始初始化MQTT客户端");
		log.info("配置信息: productKey={}, deviceName={}, serverHost={}:{}",
			productKey, deviceName, serverHost, serverPort);

		try {
			// 初始化设备模拟器
			DeviceSimulator simulator = new DeviceSimulator(
				productKey,
				deviceName,
				deviceSecret,
				serverHost,
				serverPort
			);

			// 启动模拟器
			simulator.start();

			log.info("设备模拟器启动成功");

			// 添加关闭钩子
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				log.info("应用关闭，停止设备模拟器");
				simulator.stop();
			}));

		} catch (Exception e) {
			log.error("启动设备模拟器失败", e);
		}
	}
}
