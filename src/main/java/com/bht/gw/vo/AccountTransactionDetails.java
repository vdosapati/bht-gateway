package com.bht.gw.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountTransactionDetails {
	
	private String accountNumber;
	private int totalAmount;
	private String currency;
	private List<Transaction> transactions;

}
