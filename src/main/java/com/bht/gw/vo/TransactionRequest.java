package com.bht.gw.vo;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TransactionRequest {
	
	@NotNull(message="acct number can not be empty")
	private String accountNumber;
	private Transaction transaction;
	

}
