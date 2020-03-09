package com.twinkle.proxy.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;

import java.io.OutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImgUtils {

	private static ImgUtils instance = new ImgUtils();

	private ImgUtils() {
	}

	public static ImgUtils getInstance() {
		return instance;
	}

	public void saveSecret(InputStream is, String filename, String savePath) throws Exception {

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}

	public boolean deleteImg(String path) throws Exception {

		File dir = new File(path);

		if (dir.isDirectory()) {
			String[] children = dir.list();

			for (int i = 0; i < children.length; i++) { // 递归删除目录中的子目录下
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	private boolean deleteDir(File file) {
		// TODO 自动生成的方法存根
		return file.delete();
	}

	public void saveImge(BufferedImage bufferedImage, File output) {

		try (FileOutputStream fos = new FileOutputStream(output)) {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufferedImage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
