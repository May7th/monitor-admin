package com.zy.admin.system.security.support.validate.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import com.zy.admin.system.config.support.properties.ShzProperties;
import com.zy.admin.system.security.support.validate.ValidateCodeGenerator;

public class ImageCodeGenerator implements ValidateCodeGenerator {

	private Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 28); // 字体
	
	@Autowired
	private ShzProperties shzProperties;

	@Override
	public ImageCode generator(ServletWebRequest req) {
		int width = shzProperties.getSecurity().getImageCode().getWidth();
		int height= shzProperties.getSecurity().getImageCode().getHeight();

		char[] strs = alphas();
		BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		AlphaComposite ac3;
		Color color;
		int len = strs.length;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 随机画干扰的蛋蛋
		for (int i = 0; i < 15; i++) {
			color = color(150, 250);
			g.setColor(color);
			g.drawOval(num(width), num(height), 5 + num(10), 5 + num(10));// 画蛋蛋，有蛋的生活才精彩
			color = null;
		}
		g.setFont(font);
		int h = height - ((height - font.getSize()) >> 1), w = width / len, size = w - font.getSize() + 1;
		/* 画字符串 */
		String sRand = "";
		for (int i = 0; i < len; i++) {
			ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);// 指定透明度
			g.setComposite(ac3);
			color = new Color(20 + num(110), 20 + num(110), 20 + num(110));// 对每个字符都用随机颜色
			g.setColor(color);
			g.drawString(strs[i] + "", (width - (len - i) * w) + size, h - 4);
			sRand += strs[i];
			color = null;
			ac3 = null;
		}
		return new ImageCode(bi, sRand, shzProperties.getSecurity().getImageCode().getExpireIn());
	}

	private Color color(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + num(bc - fc);
		int g = fc + num(bc - fc);
		int b = fc + num(bc - fc);
		return new Color(r, g, b);
	}

	private static final Random RANDOM = new Random();
	// 定义验证码字符.去除了O和I等容易混淆的字母
	public static char ALPHA[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 产生两个数之间的随机数
	 * 
	 * @param min
	 *            小数
	 * @param max
	 *            比min大的数
	 * @return int 随机数字
	 */
	public static int num(int min, int max) {
		return min + RANDOM.nextInt(max - min);
	}

	/**
	 * 产生0--num的随机数,不包括num
	 * 
	 * @param num
	 *            数字
	 * @return int 随机数字
	 */
	public static int num(int num) {
		return RANDOM.nextInt(num);
	}

	public static char alpha() {
		return ALPHA[num(0, ALPHA.length)];
	}

	public char[] alphas() {
		char[] cs = new char[shzProperties.getSecurity().getImageCode().getLength()];
		for (int i = 0; i < shzProperties.getSecurity().getImageCode().getLength(); i++) {
			cs[i] = alpha();
		}
		String chars = new String(cs);
		return cs;
	}

}
