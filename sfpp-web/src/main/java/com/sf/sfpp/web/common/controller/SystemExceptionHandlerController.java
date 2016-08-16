package com.sf.sfpp.web.common.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.dto.SystemConstants;
import com.sf.sfpp.common.dto.SystemErrorCode;
import com.sf.sfpp.common.exception.BaseCheckedException;
import com.sf.sfpp.common.exception.BaseRemoteAccessException;
import com.sf.sfpp.common.exception.BaseRuntimeException;
import com.sf.sfpp.web.common.utils.JsonResultUtils;

import java.security.NoSuchAlgorithmException;

/**
 * 系统异常处理
 * 
 * @author <a href="mailto:tangjimo@sf-express.com">709166</a>
 * @since version 1.0
 */
@ControllerAdvice
public class SystemExceptionHandlerController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(value={Throwable.class})
	@ResponseBody
	public JsonResult<?> handleAllException(Throwable ex) {
		//find the orginal target Exception
		//Throwable targetException = extractTargetException(ex);
		
		logger.error("", ex);
		
		String errorCode = SystemErrorCode.SYSTEM_HAS_ERROR;
		String message = null;
		if (ex instanceof BaseRuntimeException) {
			errorCode = ((BaseRuntimeException) ex).getCode();
			message = messageSource.getMessage(errorCode, ((BaseRuntimeException) ex).getMessages(), null);
		} 
		
		if (ex instanceof BaseRemoteAccessException) {
			errorCode = ((BaseRemoteAccessException) ex).getErrorCode();
			message = ((BaseRemoteAccessException) ex).getErrorMessage();
		} 
		
		if (ex instanceof BaseCheckedException) {
			errorCode = ((BaseCheckedException) ex).getCode();
			message = messageSource.getMessage(errorCode, ((BaseCheckedException) ex).getMessages(), null);
		}
		
		if (ex instanceof MissingServletRequestParameterException){
			errorCode = SystemErrorCode.PARAMETER_HAS_ERROR;
			message = messageSource.getMessage(SystemErrorCode.PARAMETER_HAS_ERROR, null, null);
		} 

		if(ex instanceof NullPointerException){
			errorCode = SystemErrorCode.BIZ_NULLPOINTER_ERROR;
			message = messageSource.getMessage(SystemErrorCode.BIZ_NULLPOINTER_ERROR,null,null);
		}
		if(ex instanceof NoSuchAlgorithmException){
			errorCode = SystemErrorCode.SYSTEM_HAS_ERROR;
			message = messageSource.getMessage(SystemErrorCode.SYSTEM_HAS_ERROR,null,null);
		}
		if(ex instanceof ClassCastException){
			errorCode = SystemErrorCode.SYSTEM_CAST_ERROR;
			message = messageSource.getMessage(SystemErrorCode.SYSTEM_CAST_ERROR,null,null);
		}


		if (message == null || message.length() == 0) {
			try {
				message = messageSource.getMessage(ex.getMessage(), null, null);
			} catch (Exception e) {
				message = messageSource.getMessage(SystemErrorCode.SYSTEM_HAS_ERROR, null, null);
			}
		}
		
		JsonResult<?> result = JsonResultUtils.getJsonResult(null, SystemConstants.RESPONSE_STATUS_FAILURE, errorCode, message);
		return result;
	}

	
}
