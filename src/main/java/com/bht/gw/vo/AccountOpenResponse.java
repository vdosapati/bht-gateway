package com.bht.gw.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AccountOpenResponse extends Response{
	private String customerId;
	private AccountTransactionDetails transactionDetails;
	private AccountDetailResponse accountDetailResponse;
	private TransactionsDetailResponse transactionsDetailResponse; 
}
