package com.sf.sfpp.common.exception;

/**
 * @author <a href="mailto:tangjimo@sf-express.com">709166</a>
 * @since version1.0 
 */
public class ArgumentException extends BaseRuntimeException {

	public ArgumentException() {
		super();
	}

	public ArgumentException(String code, Object[] messages, Throwable cause) {
		super(code, messages, cause);
	}

	public ArgumentException(String code, Object[] messages) {
		super(code, messages);
	}
	
	public ArgumentException(String code) {
		super(code);
	}

	public ArgumentException(Throwable cause) {
		super(cause);
	}

}
