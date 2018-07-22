package com.bht.gw.vo;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountOpenRequest {
	
	@NotNull(message="customerId can not be empty")
	private String customerId;
	private Integer intialCredit;
	

}
