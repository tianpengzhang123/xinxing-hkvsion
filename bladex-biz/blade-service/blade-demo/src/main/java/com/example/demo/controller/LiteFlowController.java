package com.example.demo.controller;

import com.example.demo.rule.liteflow.context.BizContext;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LiteFlowController
 *
 * @author BladeX
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("liteflow")
@Tag(name = "LiteFlow接口", description = "LiteFlow")
public class LiteFlowController {
	@Resource
	private FlowExecutor flowExecutor;

	@GetMapping("/test")
	public String test() {
		// 构建上下文
		BizContext bizContext = new BizContext();
		bizContext.setId(1L);
		bizContext.setName("测试名称");
		bizContext.setCategory(1);
		bizContext.setIsPublish(Boolean.TRUE);
		// 启动 demoChain 的规则引擎
		LiteflowResponse resp = flowExecutor.execute2Resp("demoChain", null, bizContext);
		if (resp.isSuccess()) {
			BizContext contextBean = resp.getContextBean(BizContext.class);
			return JsonUtil.toJson(contextBean);
		} else {
			return "fail";
		}
	}

}
