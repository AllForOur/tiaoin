package com.tiaoin.crawl.common.exception;

import com.tiaoin.crawl.common.enums.BaseResultCode;


/**
 * 通用的业务层异常类
 * @author yangxing
 *
 */
public class ServiceException extends RuntimeException {
	
    /** 错误代码*/
    private BaseResultCode errorCode;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(BaseResultCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public ServiceException(String message, BaseResultCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ServiceException(String message, Throwable cause, BaseResultCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public ServiceException(Throwable cause, BaseResultCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public BaseResultCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(BaseResultCode errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
