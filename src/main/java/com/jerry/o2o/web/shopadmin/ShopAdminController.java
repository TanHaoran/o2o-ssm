package com.jerry.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopAdmin", method = { RequestMethod.GET })
public class ShopAdminController {

	/**
	 * 转发至店铺注册/编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopOperation")
	public String shopOperation() {
		return "shop/shop-operation";
	}

	/**
	 * 转发至店铺列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopList")
	public String shopList() {
		return "shop/shop-list";
	}

	/**
	 * 转发至店铺管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopManagement")
	public String shopManagement() {
		return "shop/shop-management";
	}

	/**
	 * 转发至商品类别管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productCategoryManagement")
	public String productCategoryManagement() {
		return "shop/product-category-management";
	}

	/**
	 * 转发至商品添加/编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productOperation")
	public String productOperation() {
		return "shop/product-operation";
	}

}
