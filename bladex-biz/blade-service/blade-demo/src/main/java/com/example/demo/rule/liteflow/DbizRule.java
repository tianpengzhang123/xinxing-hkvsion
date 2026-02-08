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
package com.example.demo.rule.liteflow;

import com.example.demo.rule.liteflow.context.BizContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;


/**
 * A业务
 *
 * @author Chill
 */
@Slf4j
@LiteflowComponent(id = "dbizRule", name = "D业务")
public class DbizRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		// 获取上下文
		BizContext contextBean = this.getContextBean(BizContext.class);
		// 修改上下文
		contextBean.setName(contextBean.getName() + "-> D");

		log.info("D业务执行完毕");


	}

}
