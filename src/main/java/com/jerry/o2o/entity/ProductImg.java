package com.jerry.o2o.entity;

import java.sql.Date;

import lombok.Data;

/**
 * 商品图片详情实体类
 * 
 * @author Jerry
 *
 */
@Data
public class ProductImg {

	private Long productImgId;
	private String imgAddr;
	private String imgDesc;
	private Integer priority;
	private Date createTime;
	private Long productId;

}
