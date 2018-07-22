package com.bht.gw.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

	private String correlationId;
	private Date timestamp;
	private String statusCode;
	private String statusMessage;
}
