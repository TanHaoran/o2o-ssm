package com.jerry.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jerry.o2o.BaseTest;
import com.jerry.o2o.dto.ShopExecution;
import com.jerry.o2o.entity.Area;
import com.jerry.o2o.entity.PersonInfo;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.entity.ShopCategory;
import com.jerry.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {

	@Autowired
	private ShopService shopService;

	@Test
	public void testAddShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();

		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);

		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试店铺1");
		shop.setShopDesc("test1");
		shop.setPhone("test1");
		shop.setShopImg("test1.img");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");

		// 这里写我们本地的图片地址
		File shopImg = new File("D:/Picture/Resource/puzzle.png");

		ShopExecution execution = shopService.addShop(shop, shopImg);

		assertEquals(ShopStateEnum.CHECK.getState(), execution.getState());
	}
}
