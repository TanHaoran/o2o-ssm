package com.jerry.o2o.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jerry.o2o.dao.ShopDao;
import com.jerry.o2o.dto.ShopExecution;
import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.enums.ShopStateEnum;
import com.jerry.o2o.exceptions.ShopOperationException;
import com.jerry.o2o.service.ShopService;
import com.jerry.o2o.util.ImageUtil;
import com.jerry.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, File shopImg) {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 给店铺信息赋初始值
			shop.setEnableStatus(ShopStateEnum.CHECK.getState());
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				// 只有抛出RuntimeException事务才能回滚
				throw new ShopOperationException("创建店铺失败");
			} else {
				if (shopImg != null) {
					// 存储图片
					try {
						addShopImg(shop, shopImg);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					// 更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	/**
	 * 给shop添加图片
	 * 
	 * @param shop
	 * @param shopImg
	 */
	private void addShopImg(Shop shop, File shopImg) {
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		// 将shop的图片赋值给shopImg字段
		shop.setShopImg(shopImgAddr);
	}

}
