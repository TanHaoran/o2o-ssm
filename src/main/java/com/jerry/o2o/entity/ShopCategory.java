package com.jerry.o2o.entity;

import java.sql.Date;

import lombok.Data;

/**
 * 店铺类别实体类
 * 
 * @author Jerry
 *
 */
@Data
public class ShopCategory {

	private Long shopCategoryId;
	private String shopCategoryName;
	private String shopCategoryDesc;
	private String shopCategoryImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private ShopCategory parent;

}
