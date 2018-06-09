package com.jerry.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {

	/**
	 * 判断验证码是否输入正确
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkValidateCode(HttpServletRequest request) {
		String validateCodeExpected = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String validateCode = HttpServletRequestUtil.getString(request, "validateCode");
		if (validateCode == null || !validateCode.equals(validateCodeExpected)) {
			return false;
		}
		return true;
	}

}
