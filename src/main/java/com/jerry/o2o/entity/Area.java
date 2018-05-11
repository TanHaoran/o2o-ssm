package com.jerry.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 区域实体类
 * 
 * @author Jerry
 *
 */
@Data
public class Area {

	/**
	 * id
	 */
	private Integer areaId;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 优先级，这里使用Integer是因为不想要默认值0
	 */
	private Integer priority;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

}
