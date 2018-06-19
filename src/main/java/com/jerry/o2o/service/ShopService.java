package com.jerry.o2o.service;

import java.io.InputStream;

import com.jerry.o2o.dto.ShopExecution;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.exceptions.ShopOperationException;

public interface ShopService {

	/**
	 * 新增一个店铺
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

	/**
	 * 根据店铺id获取店铺信息
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);

	/**
	 * 修改店铺信息
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

	/**
	 * 根据shopCondition筛选条件分页查询店铺
	 * 
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

}
