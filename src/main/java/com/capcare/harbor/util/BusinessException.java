package com.capcare.harbor.util;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable th) {
	    super(message, th);
	}

	public BusinessException(Throwable th) {
		super(th);
	}

	public String getMessage() {
		return super.getMessage();
	}
}