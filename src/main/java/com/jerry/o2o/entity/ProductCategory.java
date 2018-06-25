package com.jerry.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 商品类别实体类
 * 
 * @author Jerry
 *
 */
@Data
public class ProductCategory {

	private Long productCategoryId;
	private Long shopId;
	private String productCategoryName;
	private Integer priority;
	private Date createTime;

}
