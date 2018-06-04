package com.jerry.o2o.service;

import java.io.File;

import com.jerry.o2o.dto.ShopExecution;
import com.jerry.o2o.entity.Shop;

public interface ShopService {

	ShopExecution addShop(Shop shop, File shopImg);

}
