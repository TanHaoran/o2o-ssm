package com.jerry.o2o.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 商品实体类
 * 
 * @author Jerry
 *
 */
@Data
public class Product {

	private Long productId;
	private String productName;
	private String productDesc;
	/**
	 * 缩略图
	 */
	private String imgAddr;
	/**
	 * 正常价格
	 */
	private String normalPrice;
	/**
	 * 促销价格
	 */
	private String promotionPrice;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	/**
	 * -1:不可用, 0:下架, 1:在前端显示系统展示
	 */
	private Integer enableStatus;

	private List<ProductImg> productImgList;
	private ProductCategory productCategory;
	private Shop shop;

}
