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
package com.xinxing.hkvision.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.xinxing.hkvision.props.HkvisionProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Hkvision控制器
 *
 * @author Chill
 */
@RefreshScope
@RestController
@RequestMapping("hkvision")
@Tag(name = "海康威视接口", description = "海康威视相关接口")
public class HkvisionController {

	/**
	 * API网关的后端服务上下文为：/artemis
	 */
	private static final String ARTEMIS_PATH = "/artemis";

	@Value("192.1.37.200:1443")
	private String artemisHost;

	@Value("26906080")
	private String appKey;

	@Value("iB4GgvznyDx2dgdXpU7d")
	private String appSecret;

	/**
	 * 需要导入xinxing-hkvision-dev.yaml文件至nacos
	 */
	@Value("${hkvision.name:1}")
	private String name;

	@Autowired
	private HkvisionProperties properties;


	@GetMapping("name")
	@Operation(summary = "获取服务名称", description = "通过配置中心获取服务名称")
	public String getName() {
		return name;
	}

	@GetMapping("name-by-props")
	@Operation(summary = "获取服务名称(属性方式)", description = "通过Properties方式获取服务名称")
	public String getNameByProps() {
		return properties.getName();
	}

	@GetMapping("health")
	@Operation(summary = "健康检查", description = "服务健康检查接口")
	public String health() {
		return "xinxing-hkvision service is running";
	}

	/**
	 * 获取抓拍图片
	 * @param request 请求参数，包含cameraIndexCode
	 * @return 返回包含picUrl的响应结果
	 */
	@PostMapping("capture")
	@Operation(summary = "获取抓拍图片", description = "根据摄像头编码获取抓拍图片URL")
	public R<String> getCapturePicture(@RequestBody Map<String, String> request) {
		try {
			String cameraIndexCode = request.get("cameraIndexCode");

			if (cameraIndexCode == null || cameraIndexCode.isEmpty()) {
				return R.fail("摄像头唯一标识编码不能为空");
			}

			ArtemisConfig config = new ArtemisConfig();
			config.setHost(artemisHost);
			config.setAppKey(appKey);
			config.setAppSecret(appSecret);

			final String manualCaptureApi = ARTEMIS_PATH + "/api/video/v1/manualCapture";

			// 构建请求体
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("cameraIndexCode", cameraIndexCode);
			String body = jsonBody.toJSONString();

			// 构建请求路径
			Map<String, String> path = new HashMap<String, String>(2) {
				{
					put("https://", manualCaptureApi);
				}
			};

			// 调用接口
			String result = ArtemisHttpUtil.doPostStringArtemis(config, path, body, null, null, "application/json");

			// 解析返回结果，提取picUrl
			JSONObject resultJson = JSON.parseObject(result);
			if (resultJson != null && resultJson.getString("code") != null && "0".equals(resultJson.getString("code"))) {
				JSONObject dataJson = resultJson.getJSONObject("data");
				if (dataJson != null) {
					String picUrl = dataJson.getString("picUrl");
					return R.data(picUrl);
				}
			}

			return R.fail("获取抓拍图片失败: " + result);
		} catch (Exception e) {
			return R.fail("获取抓拍图片失败: " + e.getMessage());
		}
	}

}
