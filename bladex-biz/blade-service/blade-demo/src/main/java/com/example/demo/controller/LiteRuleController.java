package com.example.demo.controller;

import com.example.demo.rule.literule.context.Address;
import com.example.demo.rule.literule.context.OrderContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.literule.engine.RuleEngineExecutor;
import org.springblade.core.literule.provider.LiteRuleResponse;
import org.springblade.core.literule.provider.RuleConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.Executors;

/**
 * LiteRuleController
 *
 * @author BladeX
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("literule")
@Tag(name = "LiteRule接口", description = "LiteRule")
public class LiteRuleController {
	@Resource
	private RuleEngineExecutor ruleExecutor;

	@GetMapping("/test")
	public String test() {
		// 测试成功场景
		testSuccessCase(ruleExecutor);

		// 测试失败场景
		testFailureCase(ruleExecutor);

		// 测试异步执行
		testAsyncExecution(ruleExecutor);

		return "执行完毕请查看控制台输出";
	}

	/**
	 * 测试成功场景
	 */
	private static void testSuccessCase(RuleEngineExecutor ruleExecutor) {
		log.info("=== [1] 开始测试：正常场景 ===");

		// 构建上下文
		OrderContext context = OrderContext.builder()
			.orderId("ORD-001")
			.amount(new BigDecimal("1200"))
			.paymentType("ALIPAY")
			.shippingAddress(Address.builder()
				.country("China")
				.province("Guangdong")
				.city("Shenzhen")
				.detail("Nanshan District")
				.build())
			.build();

		// 构建配置
		RuleConfig config = RuleConfig.builder()
			.enableTimeMonitor(true)
			.printExecutionTime(true)
			.enableLogging(true)
			.build();

		// 执行规则链
		LiteRuleResponse<OrderContext> response = ruleExecutor.execute(
			"orderChain",
			context,
			config
		);

		// 处理响应
		log.info("[1.1] 规则链执行结果：{}", response.isSuccess() ? "成功" : "失败");
		log.info("[1.2] 规则链执行耗时：{}毫秒", response.getExecutionTime());
		log.info("[1.3] 订单状态：{}", response.getContext().getStatus());
		log.info("[1.4] 最终金额：{}", response.getContext().getAmount());
	}

	/**
	 * 测试失败场景
	 */
	private static void testFailureCase(RuleEngineExecutor ruleExecutor) {
		log.info("=== [2] 开始测试：失败场景 ===");

		// 构建上下文（缺少必要字段）
		OrderContext context = OrderContext.builder()
			.orderId("")  // 空订单ID
			.amount(new BigDecimal("-100"))  // 负数金额
			.paymentType("UNKNOWN")  // 未知支付方式
			.shippingAddress(Address.builder()
				.country("USA")
				.province("California")
				.city("San Francisco")
				.detail("Silicon Valley")
				.build())
			.build();

		// 执行规则链
		LiteRuleResponse<OrderContext> response = ruleExecutor.execute(
			"orderChain",
			context
		);

		// 处理响应
		log.info("[2.1] 规则链执行结果：{}", response.isSuccess() ? "成功" : "失败");
		log.info("[2.2] 错误信息：{}", response.getContext().getErrorMessages());
	}

	/**
	 * 测试异步执行
	 */
	private static void testAsyncExecution(RuleEngineExecutor ruleExecutor) {
		log.info("=== [3] 开始测试：异步执行 ===");

		// 构建上下文
		OrderContext context = OrderContext.builder()
			.orderId("ORD-002")
			.amount(new BigDecimal("800"))
			.paymentType("WECHAT")
			.shippingAddress(Address.builder()
				.country("USA")
				.province("California")
				.city("San Francisco")
				.detail("Silicon Valley")
				.build())
			.build();

		// 异步执行规则链
		ruleExecutor.executeAsync(
			"orderChain",
			context,
			Executors.newSingleThreadExecutor()
		).thenAccept(response -> {
			log.info("[3.1] 异步规则链执行完成");
			log.info("[3.2] 规则链执行结果：{}", response.isSuccess() ? "成功" : "失败");
			log.info("[3.3] 规则链执行耗时：{}毫秒", response.getExecutionTime());
			log.info("[3.4] 订单状态：{}", response.getContext().getStatus());
			log.info("[3.5] 最终金额：{}", response.getContext().getAmount());
		});

		// 等待异步执行完成
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
