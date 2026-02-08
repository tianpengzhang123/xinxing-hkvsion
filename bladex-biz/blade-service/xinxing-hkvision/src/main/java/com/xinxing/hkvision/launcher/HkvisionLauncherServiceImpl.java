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
package com.xinxing.hkvision.launcher;

import org.springblade.core.auto.service.AutoService;
import org.springblade.core.launch.constant.NacosConstant;
import org.springblade.core.launch.service.LauncherService;
import org.springblade.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author Chill
 */
@AutoService(LauncherService.class)
public class HkvisionLauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		// 开启多数据源
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "true");
		// 强制禁用安全验证（覆盖 Nacos 公共配置）
		PropsUtil.setProperty(props, "blade.secure.enabled", "false");
		// 强制添加接口白名单
		PropsUtil.setProperty(props, "blade.secure.skip-url[0]", "/hkvision/**");
		PropsUtil.setProperty(props, "blade.secure.skip-url[1]", "/xinxing-hkvision/**");
		PropsUtil.setProperty(props, "blade.secure.skip-url[2]", "/favicon.ico");
		// 输出配置日志
		System.out.println("=================== xinxing-hkvision Secure Config ===================");
		System.out.println("blade.secure.enabled: " + props.getProperty("blade.secure.enabled"));
		System.out.println("blade.secure.skip-url[0]: " + props.getProperty("blade.secure.skip-url[0]"));
		System.out.println("============================================================");
		// 指定注册配置信息
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.extension-configs[0].data-id", NacosConstant.dataId("xinxing-hkvision", profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.extension-configs[0].group", NacosConstant.NACOS_CONFIG_GROUP);
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.extension-configs[0].refresh", NacosConstant.NACOS_CONFIG_REFRESH);
		// 指定注册IP
		// PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.ip", "127.0.0.1");
		// 指定注册端口
		// PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.port", "8223");
		// 自定义命名空间
		// PropsUtil.setProperty(props, "spring.cloud.nacos.config.namespace", LauncherConstant.NACOS_NAMESPACE);
		// PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.namespace", LauncherConstant.NACOS_NAMESPACE);
		// 自定义分组
		// PropsUtil.setProperty(props, "spring.cloud.nacos.config.group", NacosConstant.NACOS_CONFIG_GROUP);
		// PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.group", NacosConstant.NACOS_CONFIG_GROUP);
	}

	@Override
	public int getOrder() {
		return 20;
	}

}
