package com.jerry.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.jerry.o2o.entity.Area;
import com.jerry.o2o.entity.PersonInfo;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.entity.ShopCategory;
import com.jerry.o2o.enums.ShopStateEnum;
import com.jerry.o2o.exceptions.ShopOperationException;
import com.jerry.o2o.service.AreaService;
import com.jerry.o2o.service.ShopCategoryService;
import com.jerry.o2o.service.ShopService;
import com.jerry.o2o.util.CodeUtil;
import com.jerry.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopAdmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopservice;

	@Autowired
	private ShopCategoryService shopCategoryService;

	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/registerShop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();

		// 判断验证码是否正确
		if (!CodeUtil.checkValidateCode(request)) {
			result.put("success", false);
			result.put("errMsg", "验证码不正确");
			return result;
		}

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
			// 从Session中获取登录用户的信息
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			owner.setUserId(1l);
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = shopservice.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					result.put("success", true);
					// 取出用户可以操作的店铺列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shiopList");
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
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

	@RequestMapping(value = "/getShopInitInfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> result = new HashMap<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		List<Area> areaList = new ArrayList<>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			result.put("shopCategoryList", shopCategoryList);
			result.put("areaList", areaList);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getShopById", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopservice.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				result.put("shop", shop);
				result.put("areaList", areaList);
				result.put("success", true);
			} catch (Exception e) {
				result.put("success", false);
				result.put("errMsg", e.getMessage());
			}
		} else {
			result.put("success", false);
			result.put("errMsg", "empty shopId");
		}
		return result;
	}

	@RequestMapping(value = "/modifyShop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();

		// 判断验证码是否正确
		if (!CodeUtil.checkValidateCode(request)) {
			result.put("success", false);
			result.put("errMsg", "验证码不正确");
			return result;
		}

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
		}

		// 2. 修改店铺
		if (shop != null && shop.getShopId() != null) {
			ShopExecution se;
			try {
				if (shopImg == null) {
					se = shopservice.modifyShop(shop, null, null);
				} else {
					se = shopservice.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
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
			result.put("errMsg", "请输入店铺id");
			return result;
		}
	}

	@RequestMapping(value = "/getShopList", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();

		PersonInfo user = new PersonInfo();
		user.setUserId(1l);
		user.setName("test");
		request.getSession().setAttribute("user", user);

		user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopservice.getShopList(shopCondition, 0, 100);
			result.put("shopList", se.getShopList());
			result.put("user", user);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/getShopManagementInfo", method = RequestMethod.GET)
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();

		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId < 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				result.put("redirect", true);
				result.put("url", "/o2o/shop/shopList");
			} else {
				Shop currentShop = (Shop) currentShopObj;
				result.put("redirect", false);
				result.put("shopId", currentShop.getShopId());
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			result.put("redirect", false);
		}

		return result;
	}

}
