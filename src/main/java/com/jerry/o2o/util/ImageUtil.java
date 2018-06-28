package com.jerry.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jerry.o2o.dto.ImageHolder;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 图片处理工具类
 * 
 * @author Jerry
 *
 */
@Slf4j
public class ImageUtil {

	private static final String BASE_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random RANDOM = new Random();

	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
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
	public static String getRandomFileName() {
		// 获取随机五位数
		int randomNumber = RANDOM.nextInt(89999) + 10000;
		String nowTimeStr = DATE_FORMAT.format(new Date());
		return nowTimeStr + randomNumber;
	}

	/**
	 * 获取输入文件流的扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
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

	/**
	 * 删除文件或者目录下的所有文件和该目录
	 * 
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (File file : files) {
					file.delete();
				}
			}
			fileOrPath.delete();
		}
	}

	public static void main(String[] args) throws IOException {

		Thumbnails.of(new File("D:/Picture/Resource/puzzle.png")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(BASE_PATH + "rabbit.jpg")), 0.25f)
				.toFile("D:/Picture/Resource/puzzle_new.png");
	}

	/**
	 * 处理详情图，并返回新生成图片的相对值路径
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		// 获取不重复的随机值
		String realFileName = getRandomFileName();
		// 获取文件的扩展名
		String extension = getFileExtension(thumbnail.getImageName());
		// 如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
		// 获取文件存储的相对路径(带文件名)
		String relativeAddr = targetAddr + realFileName + extension;
		log.debug("current relativeAddr is : " + relativeAddr);
		// 获取文件要保存的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		log.debug("current complete addr is : " + PathUtil.getImgBasePath() + relativeAddr);
		// 调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(BASE_PATH + "/rabbit.jpg")), 0.25f)
					.outputQuality(0.9F).toFile(dest);
		} catch (IOException e) {
			log.error(e.toString());
			throw new RuntimeException("创建缩略图片失败" + e.toString());
		}
		// 返回图片相对路径地址
		return relativeAddr;
	}

}
