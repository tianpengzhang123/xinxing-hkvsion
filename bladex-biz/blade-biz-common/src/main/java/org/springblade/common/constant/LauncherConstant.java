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
package org.springblade.common.constant;

import org.springblade.core.launch.constant.AppConstant;

import static org.springblade.core.launch.constant.AppConstant.APPLICATION_NAME_PREFIX;

/**
 * 通用常量
 *
 * @author Chill
 */
public interface LauncherConstant {

	/**
	 * nacos 用户名
	 */
	String NACOS_USERNAME = "nacos";

	/**
	 * nacos 密码
	 */
	String NACOS_PASSWORD = "nI1214@peSMn";

	/**
	 * xxljob
	 */
	String APPLICATION_XXLJOB_NAME = APPLICATION_NAME_PREFIX + "xxljob";

	/**
	 * xxljob
	 */
	String APPLICATION_XXLJOB_ADMIN_NAME = APPLICATION_NAME_PREFIX + "xxljob-admin";

	/**
	 * nacos namespace id
	 */
	String NACOS_NAMESPACE = "f447a694-519a-4255-95f9-bcbb5a5d6369";

	/**
	 * nacos dev 地址
	 */
	String NACOS_DEV_ADDR = "10.226.16.71:8848";

	/**
	 * nacos prod 地址
	 */
	String NACOS_PROD_ADDR = "10.226.16.65:8848";

	/**
	 * nacos test 地址
	 */
	String NACOS_TEST_ADDR = "10.226.16.65:8848";

	/**
	 * sentinel dev 地址
	 */
	String SENTINEL_DEV_ADDR = "10.226.16.71:8999";

	/**
	 * sentinel prod 地址
	 */
	String SENTINEL_PROD_ADDR = "172.30.0.58:8858";

	/**
	 * sentinel test 地址
	 */
	String SENTINEL_TEST_ADDR = "172.30.0.58:8858";

	/**
	 * seata dev 地址
	 */
	String SEATA_DEV_ADDR = "10.226.16.65:8091";

	/**
	 * seata prod 地址
	 */
	String SEATA_PROD_ADDR = "172.30.0.68:8091";

	/**
	 * seata test 地址
	 */
	String SEATA_TEST_ADDR = "172.30.0.68:8091";

	/**
	 * sharding
	 */
	String APPLICATION_SHARDING_NAME = APPLICATION_NAME_PREFIX + "sharding";

	/**
	 * seata订单
	 */
	String APPLICATION_SEATA_ORDER_NAME = APPLICATION_NAME_PREFIX + "seata-order";

	/**
	 * seata库存
	 */
	String APPLICATION_SEATA_STORAGE_NAME = APPLICATION_NAME_PREFIX + "seata-storage";

	/**
	 * mqtt-broker
	 */
	String APPLICATION_MQTT_BROKER_NAME = APPLICATION_NAME_PREFIX + "mqtt-broker";

	/**
	 * mqtt-client
	 */
	String APPLICATION_MQTT_CLIENT_NAME = APPLICATION_NAME_PREFIX + "mqtt-client";

	/**
	 * rabbit-listener
	 */
	String APPLICATION_RABBIT_LISTENER_NAME = APPLICATION_NAME_PREFIX + "rabbit-listener";

	/**
	 * rabbit-publisher
	 */
	String APPLICATION_RABBIT_PUBLISHER_NAME = APPLICATION_NAME_PREFIX + "rabbit-publisher";

	/**
	 * seata file模式
	 */
	String FILE_MODE = "file";

	/**
	 * seata nacos模式
	 */
	String NACOS_MODE = "nacos";

	/**
	 * seata default模式
	 */
	String DEFAULT_MODE = "default";

	/**
	 * seata group后缀
	 */
	String GROUP_NAME = "-group";

	/**
	 * seata 服务组格式
	 *
	 * @param appName 服务名
	 * @return group
	 */
	static String seataServiceGroup(String appName) {
		return appName.concat(GROUP_NAME);
	}

	/**
	 * 动态获取nacos地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String nacosAddr(String profile) {
		return switch (profile) {
			case (AppConstant.PROD_CODE) -> NACOS_PROD_ADDR;
			case (AppConstant.TEST_CODE) -> NACOS_TEST_ADDR;
			default -> NACOS_DEV_ADDR;
		};
	}

	/**
	 * 动态获取sentinel地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String sentinelAddr(String profile) {
		return switch (profile) {
			case (AppConstant.PROD_CODE) -> SENTINEL_PROD_ADDR;
			case (AppConstant.TEST_CODE) -> SENTINEL_TEST_ADDR;
			default -> SENTINEL_DEV_ADDR;
		};
	}

	/**
	 * 动态获取seata地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String seataAddr(String profile) {
		return switch (profile) {
			case (AppConstant.PROD_CODE) -> SEATA_PROD_ADDR;
			case (AppConstant.TEST_CODE) -> SEATA_TEST_ADDR;
			default -> SEATA_DEV_ADDR;
		};
	}

}
