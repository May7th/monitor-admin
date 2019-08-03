package com.zy.admin.system.security.support.validate;

public enum ValidateCodeType {

	SMS {

		@Override
		public String getParamNameOnValidate() {
			// TODO Auto-generated method stub
			return "smsCode";
		}

	},
	IMAGE {

		@Override
		public String getParamNameOnValidate() {
			// TODO Auto-generated method stub
			return "imageCode";
		}

	};

	/**
	 * 校验时从请求中获取的参数的名字
	 * 
	 * @return
	 */
	public abstract String getParamNameOnValidate();

}
