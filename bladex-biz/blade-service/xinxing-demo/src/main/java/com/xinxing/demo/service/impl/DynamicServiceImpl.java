package com.xinxing.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xinxing.demo.mapper.NoticeMapper;
import com.xinxing.demo.pojo.entity.Notice;
import com.xinxing.demo.service.IDynamicService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DynamicServiceImpl
 *
 * @author Chill
 */
@Service
public class DynamicServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements IDynamicService {

	@Override
	public List<Notice> masterList() {
		return this.list();
	}

	@Override
	@DS("slave")
	public List<Notice> slaveList() {
		return this.list();
	}
}
