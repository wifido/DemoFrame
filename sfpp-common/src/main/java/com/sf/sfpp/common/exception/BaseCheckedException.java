package com.sf.sfpp.common.exception;

/**
 * 编译时异常系统父类
 * 
 * @author <a href="mailto:tangjimo@sf-express.com">709166</a>
 * @since version1.0 
 */
public class BaseCheckedException extends Exception {

	private static final long serialVersionUID = -6711610611939453971L;
	
	private Object[] messages;

	public BaseCheckedException() {
		super();
	}
	
	public BaseCheckedException(String code, Object[] messages, Throwable cause) {
		super(code, cause);
		this.messages = messages;
	}

	public BaseCheckedException(String code, Throwable cause) {
		this(code, null, cause);
	}
	
	public BaseCheckedException(String code) {
		super(code);
	}

	public BaseCheckedException(String code, Object[] messages) {
		this(code);
		this.messages = messages;
	}

	public BaseCheckedException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return super.getMessage();
	}
	
	public Object[] getMessages() {
		return this.messages;
	}

}
