package com.twinkle.utils;

import java.io.File;

import com.twinkle.init.CommUtils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OcrText {

	public static String ocrText(String input) {

		String secret = "";
		try {
			File imageFile = new File(input);// 图片位置
			ITesseract instance = new Tesseract(); // JNA Interface Mapping
									
			instance.setDatapath(CommUtils.INSTANCE.ocrPath());  //文件识别位置
			
			instance.setLanguage("fontyp");// 指定识别库文件
			
			
			secret = instance.doOCR(imageFile).trim();// 开始识别

			//System.out.println("识别验证码：" + secret);// 打印图片内容
			

			/*if (secret.length() != 4) {
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);

				System.out.println("请输入验证码");
				secret = scanner.nextLine();
			}*/

		} catch (TesseractException e) {
			e.printStackTrace();
		}

		return secret;
	}

}
