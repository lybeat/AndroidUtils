package com.lybeat.lilyplayer.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class SDCardUtil {

	private SDCardUtil() {
		throw new UnsupportedOperationException("Cannot be instantiated");
	}
	
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() 
				+ File.separator;
	}

	/**
	 * @return SD卡的剩余容量 单位byte
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲数据块的数量
			long availableBlocks = stat.getAvailableBlocksLong() - 4;
			// 获取单个数据块大小(byte)
			long freeBlocks = stat.getAvailableBytes();

			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 *
	 * @param filePath 指定的路径
	 * @return 可用容量字节数，单位byte
	 */
	public static long getFreeBytesWithPath(String filePath) {
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else {
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = stat.getAvailableBlocksLong() - 4;
		return stat.getBlockSizeLong() * availableBlocks;
	}
	

	public static File CreateRootFolder(String fileName) {

		String path = Environment.getExternalStorageDirectory().getPath() + "/"	+ fileName;
		File rootFile = new File(path);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
		}

		return rootFile;
	}

	/**
	 * 在根目录中创建二级目录
	 * @param rootFileName
	 * @param SecondaryFileName
	 * @return
	 */
	public static File createSecondaryFolder(String rootFileName,
			String SecondaryFileName) {

		File rootFile = CreateRootFolder(rootFileName);
		String subPath = rootFile + "/" + SecondaryFileName;
		File subFile = new File(subPath);
		if (!subFile.exists()) {
			subFile.mkdirs();
		}

		return subFile;
	}
}
