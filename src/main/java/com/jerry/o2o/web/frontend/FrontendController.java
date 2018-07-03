package com.jerry.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend", method = { RequestMethod.GET })
public class FrontendController {

	/**
	 * 首页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	private String index() {
		return "frontend/index";
	}

	/**
	 * 店铺列表路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopList")
	private String shopList() {
		return "frontend/shop-list";
	}

	/**
	 * 店铺详情路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopDetail")
	private String shopDetail() {
		return "frontend/shop-detail";
	}

}
