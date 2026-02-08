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
package com.xinxing.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

import org.springblade.core.redis.lock.RedisLock;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.constant.AuthConstant;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinxing.demo.pojo.entity.BlogEntity;
import com.xinxing.demo.pojo.vo.BlogVO;
import com.xinxing.demo.excel.BlogExcel;
import com.xinxing.demo.wrapper.BlogWrapper;
import com.xinxing.demo.service.IBlogService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.constant.RoleConstant;
import java.util.Map;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 博客表 控制器
 *
 * @author Zhujingang
 * @since 2024-10-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("blog")
@Tag(name = "博客表", description = "博客表接口")
public class BlogController extends BladeController {

	private final IBlogService blogService;

	/**
	 * 博客表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description  = "传入blog")
	public R<BlogVO> detail(BlogEntity blog) {
		BlogEntity detail = blogService.getOne(Condition.getQueryWrapper(blog));
		return R.data(BlogWrapper.build().entityVO(detail));
	}
	/**
	 * 博客表 分页
	 */
	@RedisLock(value = "lock:blog:list")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description  = "传入blog")
	public R<IPage<BlogVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> blog, Query query) {
		IPage<BlogEntity> pages = blogService.page(Condition.getPage(query), Condition.getQueryWrapper(blog, BlogEntity.class));
		return R.data(BlogWrapper.build().pageVO(pages));
	}

	/**
	 * 博客表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description  = "传入blog")
	public R<IPage<BlogVO>> page(BlogVO blog, Query query) {
		IPage<BlogVO> pages = blogService.selectBlogPage(Condition.getPage(query), blog);
		return R.data(pages);
	}

	/**
	 * 博客表 新增
	 */
	@RedisLock(value = "lock:blog:save")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description  = "传入blog")
	public R save(@Valid @RequestBody BlogEntity blog) {
		return R.status(blogService.save(blog));
	}

	/**
	 * 博客表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description  = "传入blog")
	public R update(@Valid @RequestBody BlogEntity blog) {
		return R.status(blogService.updateById(blog));
	}

	/**
	 * 博客表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description  = "传入blog")
	public R submit(@Valid @RequestBody BlogEntity blog) {
		return R.status(blogService.saveOrUpdate(blog));
	}

	/**
	 * 博客表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description  = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(blogService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 导出数据
	 */
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@GetMapping("/export-blog")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "导出数据", description  = "传入blog")
	public void exportBlog(@Parameter(hidden = true) @RequestParam Map<String, Object> blog, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<BlogEntity> queryWrapper = Condition.getQueryWrapper(blog, BlogEntity.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(Blog::getTenantId, bladeUser.getTenantId());
		//}
		queryWrapper.lambda().eq(BlogEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<BlogExcel> list = blogService.exportBlog(queryWrapper);
		ExcelUtil.export(response, "博客表数据" + DateUtil.time(), "博客表数据表", list, BlogExcel.class);
	}

}
