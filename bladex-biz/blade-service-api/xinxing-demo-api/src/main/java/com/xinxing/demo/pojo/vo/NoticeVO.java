package com.xinxing.demo.pojo.vo;

import com.xinxing.demo.pojo.entity.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告视图类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeVO extends Notice {

	@Schema(description = "通知类型名")
	private String categoryName;

}
