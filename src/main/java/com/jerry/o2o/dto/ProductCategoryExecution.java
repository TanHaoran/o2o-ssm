package com.jerry.o2o.dto;

import java.util.List;

import com.jerry.o2o.entity.ProductCategory;
import com.jerry.o2o.enums.ProductCategoryStateEnum;

import lombok.Data;

@Data
public class ProductCategoryExecution {

	/**
	 * 结果状态
	 */
	private int state;

	/**
	 * 状态标识
	 */
	private String stateInfo;

	private List<ProductCategory> productCategoryList;

	public ProductCategoryExecution() {
	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
	}

}
