package com.jerry.o2o.dto;

import java.util.List;

import com.jerry.o2o.entity.Shop;
import com.jerry.o2o.enums.ShopStateEnum;

import lombok.Data;

@Data
public class ShopExecution {

	/**
	 * 结果状态
	 */
	private int state;

	/**
	 * 状态表示
	 */
	private String stateInfo;

	/**
	 * 店铺数量
	 */
	private int count;

	/**
	 * 操作的shop（增删改查用到）
	 */
	private Shop shop;

	/**
	 * shop列表（查询店铺列表的时候用到）
	 */
	private List<Shop> shopList;

	public ShopExecution() {
	}

	/**
	 * 店铺操作失败的构造方法
	 * 
	 * @param stateEnum
	 */
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.state = stateEnum.getState();
	}

	/**
	 * 店铺操作成功的构造方法
	 * 
	 * @param stateEnum
	 */
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.state = stateEnum.getState();
		this.shop = shop;
	}

	/**
	 * 成功的构造方法
	 * 
	 * @param stateEnum
	 */
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.state = stateEnum.getState();
		this.shopList = shopList;
	}

}
