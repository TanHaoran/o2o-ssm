package com.jerry.o2o.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jerry.o2o.BaseTest;
import com.jerry.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void testQueryByShopId() {
		long shopId = 1;
		List<ProductCategory> result = productCategoryDao.queryProductCategoryList(shopId);

		assertTrue(result.size() > 0);
	}

	@Test
	public void testBatchInsertProductCategory() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1l);

		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(1l);

		List<ProductCategory> productCategoryList = new ArrayList<>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);

		int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		assertEquals(2, effectedNum);
	}

}
