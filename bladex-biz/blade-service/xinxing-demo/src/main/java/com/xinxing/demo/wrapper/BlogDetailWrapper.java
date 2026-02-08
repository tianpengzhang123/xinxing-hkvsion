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
package com.xinxing.demo.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import com.xinxing.demo.pojo.entity.BlogDetailEntity;
import com.xinxing.demo.pojo.vo.BlogDetailVO;
import java.util.Objects;

/**
 * 博客详细表 包装类,返回视图层所需的字段
 *
 * @author Zhujingang
 * @since 2024-10-18
 */
public class BlogDetailWrapper extends BaseEntityWrapper<BlogDetailEntity, BlogDetailVO>  {

	public static BlogDetailWrapper build() {
		return new BlogDetailWrapper();
 	}

	@Override
	public BlogDetailVO entityVO(BlogDetailEntity blogDetail) {
		BlogDetailVO blogDetailVO = Objects.requireNonNull(BeanUtil.copyProperties(blogDetail, BlogDetailVO.class));

		//User createUser = UserCache.getUser(blogDetail.getCreateUser());
		//User updateUser = UserCache.getUser(blogDetail.getUpdateUser());
		//blogDetailVO.setCreateUserName(createUser.getName());
		//blogDetailVO.setUpdateUserName(updateUser.getName());

		return blogDetailVO;
	}


}
