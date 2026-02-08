/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
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
import com.hikvision.artemis.sdk.Response;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.hikvision.artemis.sdk.util.HttpUtil.wrapClient;

/**
 * 海康威视Artemis接口控制器
 *
 * @author Chill
 */
@RefreshScope
@RestController
@RequestMapping("artemis")
@Tag(name = "海康威视Artemis接口", description = "海康威视Artemis相关接口")
public class ArtemisController {

	/**
	 * API网关的后端服务上下文为：/artemis
	 */
	private static final String ARTEMIS_PATH = "/artemis";

	@Value("${hkvision.artemis.host:192.1.37.200:1443}")
	private String artemisHost;

	@Value("${hkvision.artemis.appKey:26906080}")
	private String appKey;

	@Value("${hkvision.artemis.appSecret:iB4GgvznyDx2dgdXpU7d}")
	private String appSecret;

	/**
	 * 获取组织列表
	 * @param pageNo 页码
	 * @param pageSize 每页大小
	 * @return 组织列表数据
	 */
	@GetMapping("org/list")
	@Operation(summary = "获取组织列表", description = "获取海康威视组织列表")
	public R<String> getOrgList(
		@Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNo,
		@Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
		try {
			ArtemisConfig config = new ArtemisConfig();
			config.setHost(artemisHost);
			config.setAppKey(appKey);
			config.setAppSecret(appSecret);
			final String getCamsApi = ARTEMIS_PATH + "/api/resource/v1/org/orgList";
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("pageNo", String.valueOf(pageNo));
			paramMap.put("pageSize", String.valueOf(pageSize));
			String body = JSON.toJSON(paramMap).toString();
			Map<String, String> path = new HashMap<>(1) {{
				put("https://", getCamsApi);
			}};
			String result = ArtemisHttpUtil.doPostStringArtemis(config, path, body, null, null, "application/json");
			return R.data(result);
		} catch (Exception e) {
			return R.fail("获取组织列表失败: " + e.getMessage());
		}
	}

	/**
	 * 获取区域列表
	 * @param pageNo 页码
	 * @param pageSize 每页大小
	 * @param treeCode 树编码
	 * @return 区域列表数据
	 */
	@GetMapping("region/list")
	@Operation(summary = "获取区域列表", description = "获取海康威视区域列表")
	public R<String> getRegionList(
		@Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNo,
		@Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
		@Parameter(description = "树编码") @RequestParam(defaultValue = "0") String treeCode) {
		try {
			ArtemisConfig config = new ArtemisConfig();
			config.setHost(artemisHost);
			config.setAppKey(appKey);
			config.setAppSecret(appSecret);
			final String getCamsApi = ARTEMIS_PATH + "/api/resource/v1/regions";
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("pageNo", String.valueOf(pageNo));
			paramMap.put("pageSize", String.valueOf(pageSize));
			paramMap.put("treeCode", treeCode);
			String body = JSON.toJSON(paramMap).toString();
			Map<String, String> path = new HashMap<>(1) {{
				put("https://", getCamsApi);
			}};
			String result = ArtemisHttpUtil.doPostStringArtemis(config, path, body, null, null, "application/json");
			return R.data(result);
		} catch (Exception e) {
			return R.fail("获取区域列表失败: " + e.getMessage());
		}
	}

	/**
	 * 调用手动抓图接口
	 * 接口实际url：https://ip:port/artemis/api/video/v1/manualCapture
	 * @param cameraIndexCode 摄像头唯一标识编码
	 * @return 返回包含图片URL的JSON字符串
	 * @throws Exception 调用异常
	 */
	public static String callManualCapture(String cameraIndexCode) throws Exception {
		/**
		 * 手动抓图接口说明：
		 * - 协议：HTTPS
		 * - 请求方法：POST
		 * - 数据类型：application/json
		 * - 接口路径：/artemis/api/video/v1/manualCapture
		 * - 请求参数：cameraIndexCode（摄像头唯一标识编码）
		 * - 返回结果：picUrl（抓图生成的图片访问链接，包含临时访问凭证）
		 */
		ArtemisConfig config = new ArtemisConfig();
		config.setHost("192.1.37.200:1443"); // API网关服务器ip端口
		config.setAppKey("26906080");  // 合作方Key
		config.setAppSecret("iB4GgvznyDx2dgdXpU7d"); // 合作方Secret

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

		return result;
	}

	/**
	 * 获取访客记录图片
	 * @param svrIndexCode 服务器索引编码
	 * @param picUri 图片URI
	 * @return 访客记录图片URL
	 */
	@PostMapping("visitor/pictures")
	@Operation(summary = "获取访客记录图片", description = "获取访客记录图片URL")
	public R<String> getVisitorPictures(
		@Parameter(description = "服务器索引编码") @RequestBody Map<String, String> request) {
		try {
			String svrIndexCode = request.get("svrIndexCode");
			String picUri = request.get("picUri");

			if (svrIndexCode == null || svrIndexCode.isEmpty()) {
				return R.fail("服务器索引编码不能为空");
			}
			if (picUri == null || picUri.isEmpty()) {
				return R.fail("图片URI不能为空");
			}

			ArtemisConfig config = new ArtemisConfig();
			config.setHost(artemisHost);
			config.setAppKey(appKey);
			config.setAppSecret(appSecret);

			final String getSecurityApi = "/artemis/api/visitor/v1/record/pictures";

			Map<String, String> path = new HashMap<>(1) {{
				put("https://", getSecurityApi);
			}};

			JSONObject jsonBody = new JSONObject();
			jsonBody.put("svrIndexCode", svrIndexCode);
			jsonBody.put("picUri", picUri);
			String body = jsonBody.toJSONString();

			// 参数根据接口实际情况设置
			Response result = ArtemisHttpUtil.doPostStringImgArtemis(config, path, body, null, null, "application/json", null);

			if (result.getStatusCode() == 302) {
				// 获取重定向location
				String location = result.getHeader("Location");
				return R.data("图片URL", location);
			} else {
				return R.fail("获取访客记录图片失败");
			}
		} catch (Exception e) {
			return R.fail("获取访客记录图片失败: " + e.getMessage());
		}
	}






	/**
	 * 通过代理获取区域列表
	 * @param pageNo 页码
	 * @param pageSize 每页大小
	 * @param treeCode 树编码
	 * @return 区域列表数据
	 */
	@GetMapping("region/proxy")
	@Operation(summary = "通过代理获取区域列表", description = "通过外部代理服务获取区域列表")
	public R<String> getRegionListByProxy(
		@Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNo,
		@Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
		@Parameter(description = "树编码") @RequestParam(defaultValue = "0") String treeCode) {
		try {
			ArtemisConfig config = new ArtemisConfig();
			config.setHost(artemisHost);
			config.setAppKey(appKey);
			config.setAppSecret(appSecret);

			final String getCamsApi = "/proxy/api/resource/v1/regions";
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("pageNo", String.valueOf(pageNo));
			paramMap.put("pageSize", String.valueOf(pageSize));
			paramMap.put("treeCode", treeCode);
			String body = JSON.toJSON(paramMap).toString();

			Map<String, String> path = new HashMap<>(1) {{
				put("https://", getCamsApi);
			}};

			// 设置x-ca-path请求头
			Map<String, String> head = new HashMap<>(1) {{
				put("x-ca-path", "/artemis/api/api/resource/v1/regions");
			}};

			return R.data(ArtemisHttpUtil.doPostStringArtemis(config, path, body, null, null, "application/json", head));
		} catch (Exception e) {
			return R.fail("通过代理获取区域列表失败: " + e.getMessage());
		}
	}
}
