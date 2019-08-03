package com.zy.admin.system.security.support.validate;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zy.admin.system.utils.DateUtils;




public class ValidateCode implements Serializable  {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8501032369073370663L;

	private String code;
	
	private Date expireTime;



	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@JsonIgnore
	public boolean isExpried() {
		return new Date().compareTo(expireTime) > 0;
	}

	public ValidateCode (String code, int expireTime) {
		super();
		this.code = code;
		this.expireTime = DateUtils.setMinutes(new Date(), expireTime);
	}

	public ValidateCode(String code, Date expireTime) {
		super();
		this.code = code;
		this.expireTime = expireTime;
	}
	
	
	public ValidateCode() {
		super();
	}

	@Override
	public String toString() {
		return "ValidateCode [code=" + code + ", expireTime=" + expireTime + "]";
	}
	
	
	

}
