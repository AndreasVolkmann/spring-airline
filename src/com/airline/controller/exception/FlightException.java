/**
 * 
 */
package com.airline.controller.exception;

import java.io.Serializable;

/**
 * @author
 *
 */
public class FlightException extends Exception implements Serializable{

	Integer httpError;
	Integer errorCode;
	String message;
	
	public FlightException(Integer httpError,Integer errorCode,String message){
		this.httpError= httpError;
		this.errorCode= errorCode;
		this.message=message;
	}
	public Integer getHttpError() {
		return httpError;
	}
	public void setHttpError(Integer httpError) {
		this.httpError = httpError;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
