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
package com.xinxing.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.xinxing.demo.pojo.entity.BlogDetailEntity;
import com.xinxing.demo.pojo.vo.BlogDetailVO;
import com.xinxing.demo.excel.BlogDetailExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * 博客详细表 服务类
 *
 * @author Zhujingang
 * @since 2024-10-18
 */
public interface IBlogDetailService extends BaseService<BlogDetailEntity> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param blogDetail
	 * @return
	 */
	IPage<BlogDetailVO> selectBlogDetailPage(IPage<BlogDetailVO> page, BlogDetailVO blogDetail);


	/**
	 * 导出数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<BlogDetailExcel> exportBlogDetail(Wrapper<BlogDetailEntity> queryWrapper);

}
