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

import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinxing.demo.pojo.entity.BlogDetailEntity;
import com.xinxing.demo.pojo.vo.BlogDetailVO;
import com.xinxing.demo.excel.BlogDetailExcel;
import com.xinxing.demo.wrapper.BlogDetailWrapper;
import com.xinxing.demo.service.IBlogDetailService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.constant.RoleConstant;
import java.util.Map;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 博客详细表 控制器
 *
 * @author Zhujingang
 * @since 2024-10-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("blogDetail")
@Tag(name = "博客详细表", description = "博客详细表接口")
public class BlogDetailController extends BladeController {

	private final IBlogDetailService blogDetailService;

	/**
	 * 博客详细表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description  = "传入blogDetail")
	public R<BlogDetailVO> detail(BlogDetailEntity blogDetail) {
		BlogDetailEntity detail = blogDetailService.getOne(Condition.getQueryWrapper(blogDetail));
		return R.data(BlogDetailWrapper.build().entityVO(detail));
	}
	/**
	 * 博客详细表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description  = "传入blogDetail")
	public R<IPage<BlogDetailVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> blogDetail, Query query) {
		IPage<BlogDetailEntity> pages = blogDetailService.page(Condition.getPage(query), Condition.getQueryWrapper(blogDetail, BlogDetailEntity.class));
		return R.data(BlogDetailWrapper.build().pageVO(pages));
	}

	/**
	 * 博客详细表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description  = "传入blogDetail")
	public R<IPage<BlogDetailVO>> page(BlogDetailVO blogDetail, Query query) {
		IPage<BlogDetailVO> pages = blogDetailService.selectBlogDetailPage(Condition.getPage(query), blogDetail);
		return R.data(pages);
	}

	/**
	 * 博客详细表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description  = "传入blogDetail")
	public R save(@Valid @RequestBody BlogDetailEntity blogDetail) {
		return R.status(blogDetailService.save(blogDetail));
	}

	/**
	 * 博客详细表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description  = "传入blogDetail")
	public R update(@Valid @RequestBody BlogDetailEntity blogDetail) {
		return R.status(blogDetailService.updateById(blogDetail));
	}

	/**
	 * 博客详细表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description  = "传入blogDetail")
	public R submit(@Valid @RequestBody BlogDetailEntity blogDetail) {
		return R.status(blogDetailService.saveOrUpdate(blogDetail));
	}

	/**
	 * 博客详细表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description  = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(blogDetailService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 导出数据
	 */
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@GetMapping("/export-blogDetail")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "导出数据", description  = "传入blogDetail")
	public void exportBlogDetail(@Parameter(hidden = true) @RequestParam Map<String, Object> blogDetail, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<BlogDetailEntity> queryWrapper = Condition.getQueryWrapper(blogDetail, BlogDetailEntity.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(BlogDetail::getTenantId, bladeUser.getTenantId());
		//}
		queryWrapper.lambda().eq(BlogDetailEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<BlogDetailExcel> list = blogDetailService.exportBlogDetail(queryWrapper);
		ExcelUtil.export(response, "博客详细表数据" + DateUtil.time(), "博客详细表数据表", list, BlogDetailExcel.class);
	}

}
