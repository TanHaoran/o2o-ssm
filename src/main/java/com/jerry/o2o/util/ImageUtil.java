package com.jerry.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 图片处理工具类
 * 
 * @author Jerry
 *
 */
public class ImageUtil {

	private static final String BASE_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random RANDOM = new Random();

	public static String generateThumbnail(File thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(BASE_PATH + "/rabbit.jpg")), 0.25f)
					.outputQuality(0.8F).toFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
	 * 
	 * @return
	 */
	private static String getRandomFileName() {
		// 获取随机五位数
		int randomNumber = RANDOM.nextInt(89999) + 10000;
		String nowTimeStr = DATE_FORMAT.format(new Date());
		return nowTimeStr + randomNumber;
	}

	/**
	 * 获取输入文件流的扩展名
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(File file) {
		String originalFileName = file.getName();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 创建目标路径所涉及到的目录，即/home/work/jerry/xxx.jpg
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	public static void main(String[] args) throws IOException {

		Thumbnails.of(new File("D:/Picture/Resource/puzzle.png")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(BASE_PATH + "rabbit.jpg")), 0.25f)
				.toFile("D:/Picture/Resource/puzzle_new.png");
	}

}
