package com.jerry.o2o.web.shopadmin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.o2o.dto.ShopExecution;
import com.jerry.o2o.entity.PersonInfo;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.enums.ShopStateEnum;
import com.jerry.o2o.exceptions.ShopOperationException;
import com.jerry.o2o.service.ShopService;
import com.jerry.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopAdmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopservice;

	@RequestMapping(value = "/registerShop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();

		// 1. 接受并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.getMessage());
			return result;
		}
		// 将CommonsMultipartFile转换成File
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检测文件是否有上传文件流
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			result.put("success", false);
			result.put("errMsg", "上传图片不能为空");
			return result;
		}

		// 2. 注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			// Session TODO
			owner.setUserId(1l);
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = shopservice.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				result.put("success", false);
				result.put("errMsg", e.getMessage());
			} catch (IOException e) {
				result.put("success", false);
				result.put("errMsg", e.getMessage());
			}
			return result;
		} else {
			result.put("success", false);
			result.put("errMsg", "请输入店铺信息");
			return result;
		}
	}

}
