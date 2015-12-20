package com.lybeat.lilyplayer.utils;

import java.util.Random;

public class RandomUtil {

	public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERS = "0123456789";
	public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

	private RandomUtil() {
		throw new UnsupportedOperationException("Cannot be instantiated");
	}
	
	public static String getRandomNumbersAndLetters(int length) {
		return getRandom(NUMBERS_AND_LETTERS, length);
	}
	
	public static String getRandomNumbers(int length) {
		return getRandom(NUMBERS, length);
	}
	
	public static String getRandomLetters(int length) {
		return getRandom(LETTERS, length);
	}
	
	public static String getRandomCapitalLetters(int length) {
		return getRandom(CAPITAL_LETTERS, length);
	}
	
	public static String getRandomLowerCaseLetters(int length) {
		return getRandom(LOWER_CASE_LETTERS, length);
	}

	/**
	 * 从source字符串中得到一个指定长度的随机字符串
	 * @param source
	 * @param length
	 * @return
	 */
	public static String getRandom(String source, int length) {
		if (source.isEmpty()) {
			return null;
		}
		
        return getRandom(source.toCharArray(), length);
    }
	
	/**
	 * 从source字符串中得到一个指定长度的随机字符串
	 * @param sourceChar
	 * @param length
	 * @return
	 */
	public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

	/**
	 * 得到0到max之间的随机数
	 * @param max
	 * @return
	 */
	public static int getRandom(int max) {
        return getRandom(0, max);
    }

	/**
	 * 得到min到max之间的随机数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min, int max) {
        if (min == max) {
            return min;
        }
        if (min > max) {
            return max + new Random().nextInt(min - max);
        }
		return min + new Random().nextInt(max - min);
	}
}
