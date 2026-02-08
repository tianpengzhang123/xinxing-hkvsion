package com.example.demo.rule.liteflow.context;

import lombok.Data;

/**
 * 上下文类
 *
 * @author Chill
 */
@Data
public class BizContext {

	private Long id;

	private String name;

	private Integer category;

	private Boolean isPublish;

}
