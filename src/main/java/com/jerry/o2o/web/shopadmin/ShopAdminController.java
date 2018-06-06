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

}
