package com.zy.admin.system.security.support.validate.image;

import java.awt.image.BufferedImage;

import com.zy.admin.system.security.support.validate.ValidateCode;


public class ImageCode  extends ValidateCode{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7344892729586935940L;
	private BufferedImage image;
	

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public ImageCode(BufferedImage image, String code, int expireTime) {
		super(code,expireTime);
		this.image = image;
	}
	
	

}
