package com.jerry.o2o.web.shopadmin;

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
import com.jerry.o2o.dto.ImageHolder;
import com.jerry.o2o.dto.ProductExecution;
import com.jerry.o2o.entity.Product;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.enums.ProductStateEnum;
import com.jerry.o2o.exceptions.ProductOperationException;
import com.jerry.o2o.service.ProductService;
import com.jerry.o2o.util.CodeUtil;
import com.jerry.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopAdmin")
public class ProductManagementController {

	@Autowired
	private ProductService productService;

	/**
	 * 支持上传商品详情图的最大数量
	 */
	private static final int IMAGE_MAX_COUNT = 6;

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		if (!CodeUtil.checkValidateCode(request)) {
			result.put("success", false);
			result.put("errMsg", "输入了错误的验证码");
			return result;
		}
		// 接受前端参数的变量的初始化，包装商品、缩略图、详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 若请求中存在文件流，则去除相关的文件
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构建ImageHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
				// 取出详情图列表并构建List<ImageHolder>列表对象，最多支持6张图片上传
				for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImgFile != null) {
						// 若取出的详情图片文件流不为空，则将其加入详情图列表
						ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(),
								productImgFile.getOriginalFilename());
						productImgList.add(productImg);
					} else {
						// 若取出的详情图片文件流为空，则终止循环
						break;
					}
				}
			} else {
				result.put("success", false);
				result.put("errMsg", "上传图片不能为空");
				return result;
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.toString());
			return result;
		}
		try {
			// 尝试获取前端传过来的表单string流，并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.toString());
			return result;
		}
		// 若Product信息、缩略图以及详情图里诶包为空，则开始进行商品添加操作
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				// 从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				// 执行添加操作
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
				result.put("success", false);
				result.put("errMsg", e.toString());
				return result;
			}
		} else {
			result.put("success", false);
			result.put("errMsg", "请输入商品信息");
		}
		return result;
	}
}
