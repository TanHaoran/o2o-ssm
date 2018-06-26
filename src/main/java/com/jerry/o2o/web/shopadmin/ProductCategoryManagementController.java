package com.jerry.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.o2o.dto.ProductCategoryExecution;
import com.jerry.o2o.dto.Result;
import com.jerry.o2o.entity.ProductCategory;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.enums.ProductCategoryStateEnum;
import com.jerry.o2o.exceptions.ProductCategoryOperationException;
import com.jerry.o2o.service.ProductCategoryService;

@Controller
@RequestMapping("/shopAdmin")
public class ProductCategoryManagementController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getProductCategoryList", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true, list);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}
	}

	@RequestMapping(value = "/addProductCategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

		for (ProductCategory productCategory : productCategoryList) {
			productCategory.setShopId(currentShop.getShopId());
		}

		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution execution = productCategoryService
						.batchAddProductCategory(productCategoryList);
				if (execution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("errMsg", execution.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				result.put("success", false);
				result.put("errMsg", e.toString());
			}
		} else {
			result.put("success", false);
			result.put("errMsg", "请至少输入一个商品类别");
		}

		return result;
	}

}
