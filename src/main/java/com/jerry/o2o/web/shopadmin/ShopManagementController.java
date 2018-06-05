package com.jerry.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.jerry.o2o.service.ShopService;
import com.jerry.o2o.util.HttpServletRequestUtil;
import com.jerry.o2o.util.ImageUtil;
import com.jerry.o2o.util.PathUtil;

@Controller
@RequestMapping("/shop")
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
			owner.setUserId(1l);
			shop.setOwner(owner);
			File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			} catch (IOException e) {
				result.put("success", false);
				result.put("errMsg", e.getMessage());
				return result;
			}
			try {
				inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			} catch (IOException e) {
				result.put("success", false);
				result.put("errMsg", e.getMessage());
				return result;
			}
			ShopExecution se = shopservice.addShop(shop, shopImgFile);
			if (se.getState() == ShopStateEnum.CHECK.getState()) {
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("errMsg", se.getStateInfo());
			}
			return result;
		} else {
			result.put("success", false);
			result.put("errMsg", "请输入店铺信息");
			return result;
		}
	}

	/**
	 * 将InputStream转换成File
	 * 
	 * @param in
	 * @param file
	 */
	private static void inputStreamToFile(InputStream in, File file) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			int length = 0;
			byte[] buffer = new byte[1024];
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用inputStreamToFile产生异常:" + e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}

				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("inputStreamToFile关闭io产生异常:" + e.getMessage());
			}
		}
	}

}
