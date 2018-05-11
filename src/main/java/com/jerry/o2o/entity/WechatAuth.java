package com.jerry.o2o.entity;

import java.sql.Date;

import lombok.Data;

/**
 * 微信账号实体类
 * 
 * @author Jerry
 *
 */
@Data
public class WechatAuth {
	
	private Long wechatAuthId;
	private String openId;
	private Date createTime;
	private PersonInfo personInfo;

}
