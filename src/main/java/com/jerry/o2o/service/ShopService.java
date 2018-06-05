package com.jerry.o2o.service;

import java.io.InputStream;

import com.jerry.o2o.dto.ShopExecution;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.exceptions.ShopOperationException;

public interface ShopService {

	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

}
