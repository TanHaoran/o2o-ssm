package com.jerry.o2o.dto;

import lombok.Data;

@Data
public class Result<T> {
	/**
	 * 是否成功的标志
	 */
	private boolean success;

	/**
	 * 成功时返回的数据
	 */
	private T data;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	private int errorCode;

	public Result() {

	}

	/**
	 * 成功的构造方法
	 * 
	 * @param success
	 * @param data
	 */
	public Result(boolean success, T data) {
		this.success = success;
		this.data = data;
	}

	/**
	 * 错误的构造方法
	 * 
	 * @param success
	 * @param errorMsg
	 * @param errorCode
	 */
	public Result(boolean success, int errorCode, String errorMsg) {
		this.success = success;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

}
