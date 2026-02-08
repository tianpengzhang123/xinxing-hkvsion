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
package com.xinxing.demo.rule;

import com.xinxing.demo.rule.context.BizContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;


/**
 * A业务
 *
 * @author Chill
 */
@Slf4j
@LiteflowComponent(id = "cbizRule", name = "C业务")
public class CbizRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		// 获取上下文
		BizContext contextBean = this.getContextBean(BizContext.class);
		// 修改上下文
		contextBean.setName(contextBean.getName() + "-> C");

		log.info("C业务执行完毕");

	}

}
