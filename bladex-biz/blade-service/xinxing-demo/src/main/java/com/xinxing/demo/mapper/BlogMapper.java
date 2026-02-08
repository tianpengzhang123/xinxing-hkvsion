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
package com.xinxing.demo.mapper;

import com.xinxing.demo.pojo.entity.BlogEntity;
import com.xinxing.demo.pojo.vo.BlogVO;
import com.xinxing.demo.excel.BlogExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 博客表 Mapper 接口
 *
 * @author Zhujingang
 * @since 2024-10-18
 */
public interface BlogMapper extends BaseMapper<BlogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param blog
	 * @return
	 */
	List<BlogVO> selectBlogPage(IPage page, BlogVO blog);


	/**
	 * 获取导出数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<BlogExcel> exportBlog(@Param("ew") Wrapper<BlogEntity> queryWrapper);

}
