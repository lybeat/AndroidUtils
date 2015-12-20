package com.lybeat.lilyplayer.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: lybeat
 * Date: 2015/12/20
 */
public class HttpUtil {

	private static final int TIMEOUT_IN_MILLIONS = 5000;

	public interface CallBack {
		void onRequestComplete(String result);
	}

	private HttpUtil() {
		throw new UnsupportedOperationException("Cannot be instantiated");
	}

	/**
	 * 异步的Get请求
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack) {
		new Thread() {

			public void run() {
				try {
					String result = doGet(urlStr);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
	}

	/**
	 * 异步的Post请求
	 * @param urlStr
	 * @param params
	 * @param callBack
	 */
	public static void doPostAsyn(final String urlStr, final String params,
			final CallBack callBack) {
		new Thread() {

			public void run() {
				try {
					String result = doPost(urlStr, params);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * Get请求，获得返回的数据
	 * @param urlStr
	 * @return
	 */
	public static String doGet(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		ByteArrayOutputStream baos = null;

		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buffer = new byte[128];

				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				baos.flush();

				return baos.toString();
			} else {
				throw new RuntimeException("responseCode is not 200 ...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			conn.disconnect();
		}

		return null;
	}

	/**
	 * 向指定URL发送Post方法的请求
	 *
	 * @param urlStr
	 *            发送请求的url
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 所代表远程资源的响应结果
	 * @throws
	 */
	public static String doPost(String urlStr, String param) {
		PrintWriter pw = null;
		BufferedReader br = null;
		String result = "";

		URL realUrl;
		try {
			realUrl = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

			if (param != null && !param.trim().equals("")) {
				// 获取URLConnection对象对应的输出流
				pw = new PrintWriter(conn.getOutputStream());
				pw.print(param);
				pw.flush();
			}
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
