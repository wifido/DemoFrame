package com.sf.sfpp.common.exception;

/**
 * 调用服务端接口返回错误处理异常
 * 
 * 
 * @since version1.0 
 */
public class BaseRemoteAccessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	private String errorMessage;

	public BaseRemoteAccessException() {
		super();
	}

	public BaseRemoteAccessException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
