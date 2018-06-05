package com.jerry.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Http响应处理工具类
 * 
 * @author Jerry
 *
 */
public class HttpServletRequestUtil {

	/**
	 * 从请求中获取int类型参数
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 从请求中获取long类型参数
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static long getLong(HttpServletRequest request, String key) {
		try {
			return Long.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 从请求中获取double类型参数
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static double getDouble(HttpServletRequest request, String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
	}

	/**
	 * 从请求中获取boolean类型参数
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 从请求中获取String类型参数
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key) {
		try {
			String result = String.valueOf(request.getParameter(key));
			if (result != null) {
				result = result.trim();
			}
			if ("".equals(result)) {
				result = null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
