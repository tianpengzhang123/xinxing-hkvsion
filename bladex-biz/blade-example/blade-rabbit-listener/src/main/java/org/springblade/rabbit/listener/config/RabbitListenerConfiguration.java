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
package org.springblade.rabbit.listener.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit 消费者配置
 *
 * @author Chill
 */
@Configuration
public class RabbitListenerConfiguration {

	/**
	 * 示例队列名
	 */
	public static final String DEMO_QUEUE_NAME = "blade.demo.queue";

	/**
	 * 消息队列名
	 */
	public static final String MESSAGE_QUEUE_NAME = "blade.message.queue";

	/**
	 * 交换机名
	 */
	public static final String EXCHANGE_NAME = "blade.amqp.exchange";

	/**
	 * 示例路由规则
	 */
	public static final String DEMO_ROUTING_KEY_PATTERN = "demo.#";

	/**
	 * 消息路由规则
	 */
	public static final String MESSAGE_ROUTING_KEY_PATTERN = "message.#";

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	@Bean
	public Queue demoQueue() {
		return new Queue(DEMO_QUEUE_NAME);
	}

	@Bean
	public Queue messageQueue() {
		return new Queue(MESSAGE_QUEUE_NAME);
	}

	@Bean
	public Binding demoBinding(Queue demoQueue, TopicExchange exchange) {
		return BindingBuilder.bind(demoQueue).to(exchange).with(DEMO_ROUTING_KEY_PATTERN);
	}

	@Bean
	public Binding messageBinding(Queue messageQueue, TopicExchange exchange) {
		return BindingBuilder.bind(messageQueue).to(exchange).with(MESSAGE_ROUTING_KEY_PATTERN);
	}
}
