package com.jerry.o2o.enums;

import lombok.Getter;

@Getter
public enum ProductCategoryStateEnum {
	SUCCESS(1, "创建成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "添加数少于1");

	private int state;

	private String stateInfo;

	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
}
