package com.jerry.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jerry.o2o.entity.Product;

public interface ProductDao {

	/**
	 * 插入商品
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * 通过productId查询唯一id商品信息
	 * 
	 * @param productId
	 * @return
	 */
	Product queryProductByProductId(long productId);

	/**
	 * 更新商品信息
	 * 
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);

	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊）、商品状态、店铺Id、商品类别
	 * 
	 * @param productCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询对应的商品总数
	 * 
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);

	/**
	 * 删除商品之前，将商品类别id置空
	 * 
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);
}
