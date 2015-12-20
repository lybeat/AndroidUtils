package com.lybeat.lilyplayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {

	private ToastUtil() {
		throw new UnsupportedOperationException("Cannot be instantiated");
	}
	
	/**
	 * 自定义Toast的显示位置
	 * 
	 * @param c
	 * @param text
	 * @param mil
	 * @param xOffset
	 * @param yOffset
	 */
	public static void showToastCustomPosition(Context c, String text, int mil, 
			int gravity, int xOffset, int yOffset) {
		Toast toast = Toast.makeText(c, text, mil);
		toast.setGravity(gravity, xOffset, yOffset);
		toast.show();
	}
	
	/**
	 * 带图片的Toast
	 * @param c
	 * @param bmp
	 * @param mil
	 */
	public static void showToastWithImage(Context c, Bitmap bmp, int mil) {
		Toast toast = new Toast(c);
		LinearLayout layout = new LinearLayout(c);
		ImageView iv = new ImageView(c);
		iv.setImageBitmap(bmp);
		layout.addView(iv, 0);
		toast.setView(layout);
		toast.setDuration(mil);
		toast.setGravity(Gravity.BOTTOM, 0, 20);
		toast.show();
	}
	
	/**
	 * 自定义带图片的Toast的位置
	 * @param c
	 * @param bmp
	 * @param mil
	 * @param xOffset
	 * @param yOffset
	 */
	public static void showToastWithImageCustomPosition(Context c, Bitmap bmp, int mil, 
			int gravity, int xOffset, int yOffset) {
		Toast toast = new Toast(c);
		LinearLayout layout = new LinearLayout(c);
		ImageView iv = new ImageView(c);
		iv.setImageBitmap(bmp);
		layout.addView(iv);
		toast.setView(layout);
		toast.setDuration(mil);
		toast.setGravity(gravity, xOffset, yOffset);
		toast.show();
	}

	/**
	 * 显示带图片和文字的Toast，并自定义显示位置
	 * @param c
	 * @param text
	 * @param bmp
	 * @param mil
	 * @param gravity
	 * @param xOffset
	 * @param yOffset
	 */
	public static void showCustomToast(Context c, String text, Bitmap bmp, int mil, 
			int gravity, int xOffset, int yOffset) {
		LinearLayout layout = new LinearLayout(c);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(10, 10, 10, 10);
		layout.setBackgroundColor(Color.BLACK);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT);
		TextView tv = new TextView(c);
		tv.setText(text);
		tv.setTextColor(Color.WHITE);
		tv.setLayoutParams(params);
		ImageView iv = new ImageView(c);
		iv.setImageBitmap(bmp);
		iv.setLayoutParams(params);
		layout.addView(tv, 0);
		layout.addView(iv, 1);
		
		Toast toast = new Toast(c);
		toast.setView(layout);
		toast.setDuration(mil);
		toast.setGravity(gravity, xOffset, yOffset);
		toast.show();
	}
}
