package com.jerry.o2o.dao;

import static org.junit.Assert.assertTrue;

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

}
