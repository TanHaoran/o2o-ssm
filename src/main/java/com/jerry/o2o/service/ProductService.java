package com.jerry.o2o.service;

import java.util.List;

import com.jerry.o2o.dto.ImageHolder;
import com.jerry.o2o.dto.ProductExecution;
import com.jerry.o2o.entity.Product;
import com.jerry.o2o.exceptions.ProductOperationException;

public interface ProductService {

	/**
	 * 添加商品信息以及图片处理
	 * 
	 * @param product
	 * @param thumbnail
	 *            缩略图
	 * @param productImgHolderList
	 *            详情图
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException;

}
