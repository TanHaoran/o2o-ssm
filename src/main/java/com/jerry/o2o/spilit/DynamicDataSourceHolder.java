package com.jerry.o2o.spilit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicDataSourceHolder {

	/**
	 * 线程安全
	 */
	private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

	public static final String DB_MASTER = "master";
	public static final String DB_SLAVE = "slave";

	/**
	 * 获取线程的DbType
	 * 
	 * @return
	 */
	public static String getDbType() {
		String db = contextHolder.get();
		if (db == null) {
			db = DB_MASTER;
		}
		return db;
	}

	/**
	 * 设置线程的DbType
	 * 
	 * @param value
	 */
	public static void setDbType(String value) {
		log.debug("所使用的的数据源:" + value);
		contextHolder.set(value);
	}

	/**
	 * 清理连接类型
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}
}
