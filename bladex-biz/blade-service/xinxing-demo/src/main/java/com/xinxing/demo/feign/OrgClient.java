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
package com.xinxing.demo.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BladePage;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import io.swagger.v3.oas.annotations.Hidden;
import com.xinxing.demo.pojo.entity.OrgEntity;
import com.xinxing.demo.service.IOrgService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构树表 Feign实现类
 *
 * @author ZhuJinGang
 * @since 2024-10-18
 */
@Hidden
@RestController
@AllArgsConstructor
public class OrgClient implements IOrgClient {

    private final IOrgService orgService;

    @Override
    @GetMapping(TOP)
    public BladePage<OrgEntity> top(Integer current, Integer size) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        IPage<OrgEntity> page = orgService.page(Condition.getPage(query));
        return BladePage.of(page);
    }

}
