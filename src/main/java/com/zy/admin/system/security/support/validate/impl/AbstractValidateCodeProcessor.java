package com.zy.admin.system.security.support.validate.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.zy.admin.system.security.support.validate.ValidateCode;
import com.zy.admin.system.security.support.validate.ValidateCodeException;
import com.zy.admin.system.security.support.validate.ValidateCodeGenerator;
import com.zy.admin.system.security.support.validate.ValidateCodeProcessor;
import com.zy.admin.system.security.support.validate.ValidateCodeRepository;
import com.zy.admin.system.security.support.validate.ValidateCodeType;

import lombok.extern.slf4j.Slf4j;





@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {




	@Autowired
	private ValidateCodeRepository validateCodeRepository;
	
	
	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	
	/**
	 * 
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		// TODO Auto-generated method stub
       C validateCode = generate(request);
       save(request, validateCode);
       send(request, validateCode);
	}
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {
		// TODO Auto-generated method stub
		ValidateCodeType processorType = getValidateCodeType(request);
		C codeInSession = (C) validateCodeRepository.get(request,processorType);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			validateCodeRepository.remove(request,processorType);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}

		if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}

		validateCodeRepository.remove(request,processorType);
	}	
		
	
	
	/**
	 * 生成 code
	 */
	@SuppressWarnings("unchecked")
	public C generate(ServletWebRequest request) throws Exception {
		// TODO Auto-generated method stub
		String type = getValidateCodeType(request).toString().toLowerCase();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators
				.get(type + "CodeGenerator");
		return (C) validateCodeGenerator.generator(request);
	}
	
	/**
	 * 保存 code
	 */
	public void save(ServletWebRequest request,C validateCode) throws Exception {
		// TODO Auto-generated method stub
		ValidateCode vc =new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
		validateCodeRepository.save(request, vc,getValidateCodeType(request));
	}
	
	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
	    log.debug(getClass().getSimpleName());
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}
	
	
	
	
	/**
	 * 发送验证码 子类选择方式
	 */
	protected abstract void send (ServletWebRequest request,C validateCode) throws Exception;
	

}
