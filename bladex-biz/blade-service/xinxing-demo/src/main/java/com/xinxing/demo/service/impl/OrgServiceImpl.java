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
package com.xinxing.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xinxing.demo.pojo.entity.OrgEntity;
import com.xinxing.demo.pojo.vo.OrgVO;
import com.xinxing.demo.excel.OrgExcel;
import com.xinxing.demo.mapper.OrgMapper;
import com.xinxing.demo.service.IOrgService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.mp.base.BaseServiceImpl;
import java.util.List;

/**
 * 机构树表 服务实现类
 *
 * @author ZhuJinGang
 * @since 2024-10-18
 */
@Service
@DS("slave")
public class OrgServiceImpl extends BaseServiceImpl<OrgMapper, OrgEntity> implements IOrgService {

	@Override
	public IPage<OrgVO> selectOrgPage(IPage<OrgVO> page, OrgVO org) {
		return page.setRecords(baseMapper.selectOrgPage(page, org));
	}

	@Override
	public List<OrgVO> tree(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.tree(tenantId));
	}

	@Override
	public List<OrgExcel> exportOrg(Wrapper<OrgEntity> queryWrapper) {
		List<OrgExcel> orgList = baseMapper.exportOrg(queryWrapper);
		//orgList.forEach(org -> {
		//	org.setTypeName(DictCache.getValue(DictEnum.YES_NO, Org.getType()));
		//});
		return orgList;
	}

}
