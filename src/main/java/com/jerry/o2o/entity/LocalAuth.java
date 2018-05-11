package com.jerry.o2o.entity;

import java.sql.Date;

import lombok.Data;

/**
 * 本地账号实体类
 * 
 * @author Jerry
 *
 */
@Data
public class LocalAuth {

	private Long localAuthId;
	private String username;
	private String password;
	private Date createTime;
	private Date lastEditTime;
	private PersonInfo personInfo;

}
