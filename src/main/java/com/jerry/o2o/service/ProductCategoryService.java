package com.jerry.o2o.service;

import java.util.List;

import com.jerry.o2o.entity.ProductCategory;

public interface ProductCategoryService {

	/**
	 * 查询某个店铺下的所有商品类别信息
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
}
