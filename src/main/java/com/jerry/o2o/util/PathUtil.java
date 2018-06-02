package com.jerry.o2o.util;

/**
 * 路径工具类
 * 
 * @author Jerry
 */
public class PathUtil {

	private static final String SEPERATOR = System.getProperty("file.seperator");

	/**
	 * 获取图片存储路径
	 * 
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/EclipseProject/image/";
		} else {
			basePath = "/home/jerry/image";
		}
		return basePath.replace("/", SEPERATOR);
	}

	/**
	 * 获取店铺图片存储路径
	 * 
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", SEPERATOR);
	}
}
