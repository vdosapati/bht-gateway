package com.bht.gw.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatewayException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorcode;
	private String errorMessage;
	private String errorName;
	
	
	public GatewayException(String errorcode, String errorMessage, String errorName) {
		super();
		this.errorcode = errorcode;
		this.errorMessage = errorMessage;
		this.errorName = errorName;
	}
	
	public GatewayException(String errorcode, String errorMessage) {
		super();
		this.errorcode = errorcode;
		this.errorMessage = errorMessage;
	}
	
	public GatewayException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	
}
