package com.jerry.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jerry.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 新增店铺
	 * 
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);

	/**
	 * 更新店铺信息
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);

	/**
	 * 通过shop id查询店铺
	 * 
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);

	/**
	 * 分页查询店铺，可输入的条件有：店铺名（模糊）、店铺状态、店铺类别，区域id、owner
	 * 
	 * @param shopCondition
	 * @param rowIndex
	 *            从第几行开始取
	 * @param pageSize
	 *            返回的条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 返回queryShopList的总数
	 * 
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);

}
