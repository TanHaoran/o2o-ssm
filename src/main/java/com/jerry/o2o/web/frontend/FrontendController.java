package com.jerry.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend", method = { RequestMethod.GET })
public class FrontendController {

	@RequestMapping(value = "/index")
	private String index() {
		return "frontend/index";
	}

}
