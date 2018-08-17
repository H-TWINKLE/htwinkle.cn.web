package com.twinkle.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.twinkle.init.CommUtils;
import com.twinkle.proxy.utils.ImgUtils;

public class JwglImgHandle {

	

	public static void changeImge(String name,String endName) {
		Image image = null;
		
		String path = CommUtils.INSTANCE.isServicePath();
		
		try {

			image = ImageIO.read(new File(path+"//" +name ));
			name = "bToWhilte.png";
			ImgUtils.getInstance().saveImge(bToWhilte(image), new File(path +"//" + name ));

			image = ImageIO.read(new File(path +"//" + name ));
			name = "contrastTo.png";
			ImgUtils.getInstance().saveImge(contrastTo(image), new File(path +"//" + name ));

			image = ImageIO.read(new File(path +"//" + name ));
			name = "pixelTo.png";
			ImgUtils.getInstance().saveImge(pixelTo(image), new File(path +"//" + name ));
			
			image = ImageIO.read(new File(path +"//" + name ));			
			ImgUtils.getInstance().saveImge(colInverse(image), new File(path +"//" + endName +".jpg"));

		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		}
	}

	private static BufferedImage initImageBuffered(Image input) {

		BufferedImage bufferedImage = null;
		int srcH, srcW = 0;
		try {
			srcH = input.getHeight(null);
			srcW = input.getWidth(null);

			bufferedImage = new BufferedImage(srcW, srcH, BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics().drawImage(input, 0, 0, srcW, srcH, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

	private static BufferedImage bToWhilte(Image input) {
		BufferedImage bufferedImage = null;

		try {
			bufferedImage = initImageBuffered(input);
			bufferedImage = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(bufferedImage,
					null);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return bufferedImage;

	}

	private static BufferedImage contrastTo(Image input) {
		BufferedImage bufferedImage = null;

		try {
			bufferedImage = initImageBuffered(input);
			bufferedImage = new RescaleOp((float)2.55, 0, null).filter(bufferedImage, null);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return bufferedImage;
	}

	private static BufferedImage pixelTo(Image input) {
		BufferedImage bufferedImage = null;
		BufferedImage binaryBufferedImage = null;
		BufferedImage binaryBufferedImage2 = null;
		try {
			bufferedImage = initImageBuffered(input);
			int h = bufferedImage.getHeight();
			int w = bufferedImage.getWidth();
			int[][] gray = new int[w][h];
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					int argb = bufferedImage.getRGB(x, y);
					// 图像加亮（调整亮度识别率非常高）
					int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
					int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
					int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
					if (r >= 255) {
						r = 255;
					}
					if (g >= 255) {
						g = 255;
					}
					if (b >= 255) {
						b = 255;
					}
					gray[x][y] = (int) Math.pow(
							(Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2) * 0.6274 + Math.pow(b, 2.2) * 0.0753),
							1 / 2.2);
				}
			}

			// 二值化
			int threshold = ostu(gray, w, h);

			binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					if (gray[x][y] > threshold) {
						gray[x][y] |= 0x00FFFF;    //黑色
					} else {
						gray[x][y] &= 0xFF0000;
					}
					binaryBufferedImage.setRGB(x, y, gray[x][y]);
				}
				//System.out.println();

			}

			// 判断二维数组四周 像素点是否相邻

			gray = new int[w][h];
			binaryBufferedImage2 = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
			for (int y = 1; y < h - 1; y++) {
				for (int x = 1; x < w - 1; x++) {
					boolean isEqual = isBlack(binaryBufferedImage.getRGB(x, y));
					if (isEqual) {
						if (isEqual == isBlack(binaryBufferedImage.getRGB(x - 1, y))
								|| isEqual == isBlack(binaryBufferedImage.getRGB(x + 1, y))) {
						//	System.out.print("*");							
							gray[x][y] |= 0x00FFFF; 														
						}

					} else {
					//	System.out.print(" ");						
                     gray[x][y] &= 0xFF0000;
					}
					binaryBufferedImage2.setRGB(x, y, gray[x][y]);
				}
				//System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return binaryBufferedImage2;
	}

	private static boolean isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
			return true;
		}
		return false;
	}

	private static int ostu(int[][] gray, int w, int h) {
		int[] histData = new int[w * h];
		// Calculate histogram
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int red = 0xFF & gray[x][y];
				histData[red]++;
			}
		}

		// Total number of pixels
		int total = w * h;

		float sum = 0;
		for (int t = 0; t < 256; t++)
			sum += t * histData[t];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		int threshold = 0;

		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Weight Background
			if (wB == 0)
				continue;

			wF = total - wB; // Weight Foreground
			if (wF == 0)
				break;

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB; // Mean Background
			float mF = (sum - sumB) / wF; // Mean Foreground

			// Calculate Between Class Variance
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}

		return threshold;
	}
	
	
	private static BufferedImage colInverse(Image input) {
		BufferedImage bufferedImage = null;

		try {
			bufferedImage = initImageBuffered(input);
			for (int i = 0; i < bufferedImage.getHeight(); i++) { 
                for (int j = 0; j < bufferedImage.getWidth(); j++) {  
                    int pixel = bufferedImage.getRGB(j, i); 
                    bufferedImage.setRGB(j,i,0xFFFFFF-pixel);
                }
            }

		} catch (Exception e) {

			e.printStackTrace();
		}

		return bufferedImage;
	}


}
