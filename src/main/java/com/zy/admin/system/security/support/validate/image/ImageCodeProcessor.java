package com.zy.admin.system.security.support.validate.image;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.zy.admin.system.security.support.validate.impl.AbstractValidateCodeProcessor;

import lombok.extern.slf4j.Slf4j;

@Component("imageValidateCodeProcessor")
@Slf4j
public class ImageCodeProcessor extends
		AbstractValidateCodeProcessor<ImageCode> {



	@Override
	protected void send(ServletWebRequest request, ImageCode validateCode)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("图形验证码" + validateCode.getCode());
		ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse()
				.getOutputStream());
	}

}
