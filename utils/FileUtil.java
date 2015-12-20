package com.lybeat.lilyplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author: lybeat
 * Date: 2015/12/20
 */
public class FileUtil {

    private static final String TAG = "FileUtils_lybeat";

	private FileUtil() {
		throw new UnsupportedOperationException("Cannot be instantiated");
	}

	/**
	 * 遍历一个文件路径，然后把文件子目录中的所有文件遍历并输出来
	 * @param rootPath
	 * @return
	 */
	public static ArrayList<String> getAllFiles(File rootPath) {

		File[] files = rootPath.listFiles();
		ArrayList<String> paths = new ArrayList<String>();

		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					paths.addAll(getAllFiles(file));
				} else {
					paths.add(file.toString());
				}
			}
		}
		return paths;
	}

	/**
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String uriToPath(Context context, Uri uri) {
		String path = null;
		Uri fileUri = uri;
		if (uri != null) {
			// 以content://开头的uri
			if (fileUri.getScheme().toString().compareTo("content") == 0) {
				Cursor cursor = context.getContentResolver().query(fileUri,
						null, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					int columnIndex = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					path = cursor.getString(columnIndex);
					cursor.close();
				}
			} else if (fileUri.getScheme().toString().compareTo("file") == 0) {
				// 以file:///开头的Uri
				path = fileUri.toString().replace("file://", "");
			}
		}

		return path;
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public static Uri pathToUri(Context context, String path)
        throws NullPointerException {
		String type = getExtensionName(path);
		if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png")
				|| type.equals(".bmp") || type.equals(".gif")) {
			Uri baseUri = Uri.parse("content://media/external/images/media");
			Uri uri = null;

            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
            try {
                cursor.moveToFirst();
                do {
                    String str = cursor.getString(cursor
                            .getColumnIndex(MediaStore.MediaColumns.DATA));
                    if (path.equalsIgnoreCase(str)) {
                        int index = cursor.getInt(cursor
                                .getColumnIndex(MediaStore.MediaColumns._ID));
                        uri = Uri.withAppendedPath(baseUri, index + "");

                        break;
                    }
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
            return uri;
		} else {
			return null;
		}
	}
	
	/**
	 *
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		String fileName = null;
		
		if (path != null && path.length() > 0) {
			int start = path.lastIndexOf('/');
			if (start > -1 && start < (path.length()) -1) {
				fileName = path.substring(start);
			}
		}
		return fileName;
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public static String getExtensionName(String path) {
		String exName = null;
		
		if (path != null && path.length() > 0) {
			int start = path.lastIndexOf('.');
			if (start > -1 && start < (path.length() - 1)) {
				exName = path.substring(start);
			}
		}
        Log.i(TAG, "file type: " + exName);

		return exName;
	}
	
	/**
	 * 通过文件路径得到不含扩展名的文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileNameNoEx(String path) {
		String fileName = getFileName(path);
		
		 if (fileName != null && fileName.length() > 0) {
			 int end = fileName.lastIndexOf('.');
			 if (end > -1 && end < (path.length() - 1)) {
				 fileName = path.substring(0, end);
			 }
		 }
		 
		 return fileName;
	}

    /**
     *
     * @param context
     * @param path
     * @param data
     * @throws IOException
     */
	public static void saveFileData(Context context, String path, byte[] data)
			throws IOException {
		Date date = new Date();
		String name = DateUtil.getChangeDateFormat(date, "yyyyMMddHHmmss");
		name += ".txt";

		File cache = new File(path, name);
		FileOutputStream fos = new FileOutputStream(cache);
		fos.write(data);
		fos.close();
	}

    /**
     *
     * @param context
     * @param name
     * @return
     * @throws IOException
     */
	public static byte[] loadFileData(Context context, String name)
			throws IOException {
		FileInputStream in = context.openFileInput(name);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;

		try {
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
		} finally {
			in.close();
			baos.close();
		}

		return baos.toByteArray();
	}
}
