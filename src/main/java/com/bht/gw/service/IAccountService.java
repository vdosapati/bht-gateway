package com.bht.gw.service;

import com.bht.gw.vo.AccountOpenRequest;
import com.bht.gw.vo.AccountOpenResponse;
import com.bht.vo.FGRequestContext;

public interface IAccountService {

	AccountOpenResponse openAccount(FGRequestContext reqContext,AccountOpenRequest accountOpenRequest);
	AccountOpenResponse getCustomerDetails();

	
}