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
import com.xinxing.demo.pojo.entity.OrgEntity;
import com.xinxing.demo.pojo.vo.OrgVO;
import com.xinxing.demo.excel.OrgExcel;
import com.xinxing.demo.wrapper.OrgWrapper;
import com.xinxing.demo.service.IOrgService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.constant.RoleConstant;
import java.util.Map;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 机构树表 控制器
 *
 * @author ZhuJinGang
 * @since 2024-10-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("org")
@Tag(name = "机构树表", description = "机构树表接口")
public class OrgController extends BladeController {

	private final IOrgService orgService;

	/**
	 * 机构树表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description  = "传入org")
	public R<OrgVO> detail(OrgEntity org) {
		OrgEntity detail = orgService.getOne(Condition.getQueryWrapper(org));
		return R.data(OrgWrapper.build().entityVO(detail));
	}
	/**
	 * 机构树表 树列表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description  = "传入org")
	public R<List<OrgVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> org, BladeUser bladeUser) {
		QueryWrapper<OrgEntity> queryWrapper = Condition.getQueryWrapper(org, OrgEntity.class);
		List<OrgEntity> list = orgService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(OrgEntity::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(OrgWrapper.build().treeNodeVO(list));
	}

	/**
	 * 机构树表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description  = "传入org")
	public R<IPage<OrgVO>> page(OrgVO org, Query query) {
		IPage<OrgVO> pages = orgService.selectOrgPage(Condition.getPage(query), org);
		return R.data(pages);
	}

	/**
	 * 机构树表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description  = "传入org")
	public R save(@Valid @RequestBody OrgEntity org) {
		return R.status(orgService.save(org));
	}

	/**
	 * 机构树表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description  = "传入org")
	public R update(@Valid @RequestBody OrgEntity org) {
		return R.status(orgService.updateById(org));
	}

	/**
	 * 机构树表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description  = "传入org")
	public R submit(@Valid @RequestBody OrgEntity org) {
		return R.status(orgService.saveOrUpdate(org));
	}

	/**
	 * 机构树表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description  = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(orgService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 机构树表 树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "树形结构", description  = "树形结构")
	public R<List<OrgVO>> tree(String tenantId, BladeUser bladeUser) {
		List<OrgVO> tree = orgService.tree(Func.toStrWithEmpty(tenantId, bladeUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * 导出数据
	 */
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@GetMapping("/export-org")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "导出数据", description  = "传入org")
	public void exportOrg(@Parameter(hidden = true) @RequestParam Map<String, Object> org, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<OrgEntity> queryWrapper = Condition.getQueryWrapper(org, OrgEntity.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(Org::getTenantId, bladeUser.getTenantId());
		//}
		queryWrapper.lambda().eq(OrgEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<OrgExcel> list = orgService.exportOrg(queryWrapper);
		ExcelUtil.export(response, "机构树表数据" + DateUtil.time(), "机构树表数据表", list, OrgExcel.class);
	}

}
