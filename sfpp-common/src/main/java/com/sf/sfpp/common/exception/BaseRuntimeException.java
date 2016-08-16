package com.sf.sfpp.common.exception;

/**
 * 运行时系统异常父类
 * 
 * @author <a href="mailto:tangjimo@sf-express.com">709166</a>
 * @since version1.0
 */
public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6835064766067430918L;
	
	private Object[] messages;

	public BaseRuntimeException() {
		super();
	}

	public BaseRuntimeException(String code, Throwable cause) {
		this(code, null, cause);
	}

	public BaseRuntimeException(String code, Object[] messages, Throwable cause) {
		super(code, cause);
		this.messages = messages;
	}

	public BaseRuntimeException(String code) {
		super(code);
	}

	public BaseRuntimeException(String code, Object[] messages) {
		this(code);
		this.messages = messages;
	}

	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return super.getMessage();
	}
	
	public Object[] getMessages() {
		return this.messages;
	}

}
