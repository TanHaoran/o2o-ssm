package com.jerry.o2o.entity;

import java.sql.Date;

import lombok.Data;

/**
 * 头条实体类
 * 
 * @author Jerry
 *
 */
@Data
public class HeadLine {

	private Long lineId;
	private String lineName;
	private String lineLink;
	private String lineImg;
	private Integer priority;
	/**
	 * 0:不可用,1:可用
	 */
	private Integer enableStatus;
	private Date createTime;
	private Date lastEditTime;
}
