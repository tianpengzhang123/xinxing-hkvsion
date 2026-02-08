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
package com.example.demo.controller;

import com.example.demo.props.DemoProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo控制器
 *
 * @author Chill
 */
@RefreshScope
@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
@Tag(name = "配置接口", description = "即时刷新配置")
public class DemoController {

	/**
	 * 需要导入blade-demo-dev.yaml文件至nacos
	 */
	@Value("${demo.name:1}")
	private String name;

	private final DemoProperties properties;


	@GetMapping("name")
	public String getName() {
		return name;
	}

	@GetMapping("name-by-props")
	public String getNameByProps() {
		return properties.getName();
	}

}
