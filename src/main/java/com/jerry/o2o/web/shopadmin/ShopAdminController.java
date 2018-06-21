package com.jerry.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopAdmin", method = { RequestMethod.GET })
public class ShopAdminController {

	@RequestMapping(value = "/shopOperation")
	public String shopOperation() {
		return "shop/shop-operation";
	}

	@RequestMapping(value = "/shopList")
	public String shopList() {
		return "shop/shop-list";
	}

	@RequestMapping(value = "/shopManagement")
	public String shopManagement() {
		return "shop/shop-management";
	}

	@RequestMapping(value = "/productCategoryManagement")
	public String productCategoryManagement() {
		return "shop/product-category-management";
	}

}
