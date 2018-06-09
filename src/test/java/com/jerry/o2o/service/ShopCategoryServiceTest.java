package com.jerry.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jerry.o2o.BaseTest;
import com.jerry.o2o.entity.ShopCategory;

public class ShopCategoryServiceTest extends BaseTest {

	@Autowired
	private ShopCategoryService shopCategoryService;

	@Test
	public void testGetShopCategoryList() {
		List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
		assertEquals(2, shopCategoryList.size());
	}
}
