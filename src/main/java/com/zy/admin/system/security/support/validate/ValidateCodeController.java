package com.zy.admin.system.security.support.validate;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ValidateCodeController {

	
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;
	
	@RequestMapping("/code/{type}")
	public void createCode(HttpServletRequest req,HttpServletResponse res,@PathVariable String type) throws Exception{
		validateCodeProcessors.get(type+"ValidateCodeProcessor").create(new ServletWebRequest(req, res));
	}
	
	

	
	
}
