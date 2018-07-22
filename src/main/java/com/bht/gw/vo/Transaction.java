package com.bht.gw.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Transaction {

	private Integer transactionAmount;
	private String fromAccountNumber;
	private String toAccountNumber;
	private String remarks;
	
	
}
