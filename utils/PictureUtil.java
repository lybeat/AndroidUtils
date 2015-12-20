package com.lybeat.lilyplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Author: lybeat
 * Date: 2015/12/20
 */
public class PictureUtil {

    /**
     * 从路径中得到一张图片，并缩放到指定的大小
     * @param path
     * @param desWidth
     * @param desHeight
     * @return
     */
	public static Bitmap getScaledBitmapFromPath(String path, int desWidth, int desHeight) {

		// Read in the dimensions of the image on disk
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;

		int inSamplesSize;
		if (srcHeight > desHeight || srcWidth > desWidth) {
			inSamplesSize = Math.round(srcHeight / desHeight);
		} else {
			inSamplesSize = Math.round(srcWidth / desWidth);
		}

		options = new BitmapFactory.Options();
		options.inSampleSize = inSamplesSize;

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		return bitmap;
	}

	/**
	 * 从资源文件中获取一张图片，并缩放到指定大小
	 * 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	public static Bitmap getScaledBitmapFromResource(Context context, int resourceId,
													 int desWidth, int desHeight) {
		// Read in the dimensions of the image on disk
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resourceId, options);

		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;

		int inSamplesSize;
		if (srcHeight > desHeight || srcWidth > desWidth) {
			inSamplesSize = Math.round(srcHeight / desHeight);
		} else {
			inSamplesSize = Math.round(srcWidth / desWidth);
		}

		options = new BitmapFactory.Options();
		options.inSampleSize = inSamplesSize;

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId,
				options);

		return bitmap;
	}

	/**
	 *
	 * @param srcBmp
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap srcBmp) {

		final Paint paint = new Paint();

		int width = srcBmp.getWidth();
		int height = srcBmp.getHeight();
		int left, top, right, bottom;
		if (width <= height) {
			left = 0;
			top = (height - width) / 2;
			right = width;
			bottom = top + width;
		} else {
			left = (width - height) / 2;
			top = 0;
			right = left + height;
			bottom = height;
		}
		final Rect rect = new Rect(left, top, right, bottom);
		final RectF rectF = new RectF(rect);

		Bitmap desBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(desBmp);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		int radius = (int) ((width > height ? width : height) / 2);
		canvas.drawRoundRect(rectF, radius, radius, paint);

		// 设置当两个图形相交时的模式, SRC_IN为取SRC图形相交的部分, 多余的将被去掉
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(srcBmp, rect, rect, paint);

		return desBmp;
	}

	/**
	 *
	 * @param activity
	 * @param path
	 * @return
	 */
	public static Bitmap drawHandBitmap(Activity activity, String path) {

		Bitmap desBmp = BitmapFactory.decodeFile(path);
		desBmp = PictureUtil.getRoundBitmap(desBmp);

		Paint paint = new Paint();
		paint.setColor(0xffffffff);
		paint.setStrokeWidth(10);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);

		int width = desBmp.getWidth();
		int height = desBmp.getHeight();
		float cx = width / 2;
		float cy = height / 2;
		float radius = width > height ? width / 2 - 5 : height / 2 - 5;
		Canvas canvas = new Canvas(desBmp);
		canvas.drawCircle(cx, cy, radius, paint);

		return desBmp;
	}

	public static byte[] BitmapToBytes(Context context, Bitmap bmp)
			throws IOException {
		if (bmp == null)
			return null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, baos);

		return baos.toByteArray();
	}

	public static void savePictureToFile(Context context, Bitmap bmp,
			String path, String format) {
		Date date = new Date();
		String name = DateUtil.getChangeDateFormat(date, "yyyyMMddHHmmss");

		try {
			FileOutputStream fos = new FileOutputStream(path + "/" + name
					+ "." + format);
			if (format.equalsIgnoreCase("png")) {
				bmp.compress(CompressFormat.PNG, 0, fos);
			} else if (format.equalsIgnoreCase("jpeg")) {
				bmp.compress(CompressFormat.JPEG, 100, fos);
			} else if (format.equalsIgnoreCase("webp")) {
				bmp.compress(CompressFormat.WEBP, 100, fos);
			}
			fos.close();
		} catch (Exception e) {
			Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
		}
	}
}
