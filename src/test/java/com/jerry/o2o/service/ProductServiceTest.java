package com.jerry.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jerry.o2o.BaseTest;
import com.jerry.o2o.dto.ImageHolder;
import com.jerry.o2o.dto.ProductExecution;
import com.jerry.o2o.entity.Product;
import com.jerry.o2o.entity.ProductCategory;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.enums.ProductStateEnum;
import com.jerry.o2o.exceptions.ProductOperationException;
import com.jerry.o2o.exceptions.ShopOperationException;

public class ProductServiceTest extends BaseTest {

	@Autowired
	private ProductService productService;

	@Test
	public void testAddProduct() throws ShopOperationException, FileNotFoundException, ProductOperationException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1l);

		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1l);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1描述");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

		File thumbnailFile = new File("D:/Picture/Resource/9e567d380cd7912309d0fa1dab345982b3b780f9.jpg");
		InputStream in = new FileInputStream(thumbnailFile);

		ImageHolder thumbnail = new ImageHolder(in, thumbnailFile.getName());

		File productImg1 = new File("D:/Picture/Resource/IMG_7310.JPG");
		InputStream in1 = new FileInputStream(productImg1);
		File productImg2 = new File("D:/Picture/Resource/IMG_7310.JPG");
		InputStream in2 = new FileInputStream(productImg2);

		List<ImageHolder> productImgList = new ArrayList<>();
		productImgList.add(new ImageHolder(in1, productImg1.getName()));
		productImgList.add(new ImageHolder(in2, productImg2.getName()));

		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);

		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());

	}

}
