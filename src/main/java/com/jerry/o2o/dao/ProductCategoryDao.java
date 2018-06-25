package com.jerry.o2o.dao;

import java.util.List;

import com.jerry.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	/**
	 * 通过店铺id查询店铺商品类别
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);

	/**
	 * 批量新增商品类别
	 * 
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

}
