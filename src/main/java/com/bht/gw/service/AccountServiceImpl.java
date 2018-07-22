package com.bht.gw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bht.gateway.ms.util.GatewayUtil;
import com.bht.gw.exception.GatewayException;
import com.bht.gw.vo.Account;
import com.bht.gw.vo.AccountOpenRequest;
import com.bht.gw.vo.AccountOpenResponse;
import com.bht.gw.vo.AccountTransactionDetails;
import com.bht.gw.vo.AccountTransactionRequest;
import com.bht.gw.vo.Transaction;
import com.bht.gw.vo.TransactionRequest;
import com.bht.gw.vo.TransactionsDetailResponse;
import com.bht.vo.FGRequestContext;
import com.bht.vo.ServiceResponse;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private GatewayUtil util;

	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate template;

	@SuppressWarnings("unchecked")
	@Override
	public AccountOpenResponse openAccount(FGRequestContext reqContext, AccountOpenRequest accountOpenRequest) {

		AccountOpenResponse accountResponse = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("authToken", util.createJwtToken());
		headers.set("Content-Type", "application/json");
		HttpEntity reEntiry = new HttpEntity(accountOpenRequest, headers);
		ResponseEntity<ServiceResponse<AccountOpenResponse>> accountOpenResponse = template.exchange(
				env.getProperty("acctms.openAccount"), HttpMethod.POST, reEntiry,
				new ParameterizedTypeReference<ServiceResponse<AccountOpenResponse>>() {

				});
		if (accountOpenResponse.getBody().getErrorInfo().getErrorCode().equals("00")) {
			accountResponse = accountOpenResponse.getBody().getData();
			if (accountOpenRequest.getIntialCredit() != 0 && null != accountResponse.getAccountDetailResponse().getAccountDetails()) {
				Account secondaryAccount = null;
				for(Account acct:accountResponse.getAccountDetailResponse().getAccountDetails()){
					if(acct.getAccountType().equals("secondary")){
						secondaryAccount = acct;
						break;
					}
				}
				Transaction transaction = Transaction.builder()
						.fromAccountNumber(secondaryAccount.getAccountNumber())
						.toAccountNumber(secondaryAccount.getAccountNumber())
						.transactionAmount(accountOpenRequest.getIntialCredit()).remarks("self").build();
				TransactionRequest transactionRequest = new TransactionRequest();
				transactionRequest.setAccountNumber(secondaryAccount.getAccountNumber());
				transactionRequest.setTransaction(transaction);
				HttpEntity transEntity = new HttpEntity(transactionRequest, headers);
				ResponseEntity<ServiceResponse<AccountTransactionDetails>> accountTransactionDetails = template
						.exchange(env.getProperty("transactionms.details"), HttpMethod.POST, transEntity,
								new ParameterizedTypeReference<ServiceResponse<AccountTransactionDetails>>() {

								});
				if (accountTransactionDetails.getBody().getErrorInfo().getErrorCode().equals("00")) {
					accountResponse.setTransactionDetails(accountTransactionDetails.getBody().getData());

				} else {
					throw new GatewayException(accountTransactionDetails.getBody().getErrorInfo().getErrorCode(),
							accountTransactionDetails.getBody().getErrorInfo().getErrorMessage());
				}

			}
		} else {
			throw new GatewayException(accountOpenResponse.getBody().getErrorInfo().getErrorCode(),
					accountOpenResponse.getBody().getErrorInfo().getErrorMessage());
		}
		return accountResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public AccountOpenResponse getCustomerDetails() {
		AccountOpenResponse accountResponse = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("authToken", util.createJwtToken());
		headers.set("Content-Type", "application/json");
		HttpEntity reEntiry = new HttpEntity(headers);
		ResponseEntity<ServiceResponse<AccountOpenResponse>> accountOpenResponse = template.exchange(
				env.getProperty("acctms.details"), HttpMethod.GET, reEntiry,
				new ParameterizedTypeReference<ServiceResponse<AccountOpenResponse>>() {

				});
		if (accountOpenResponse.getBody().getErrorInfo().getErrorCode().equals("00")) {
			accountResponse = accountOpenResponse.getBody().getData();
			
			if ( null != accountResponse.getAccountDetailResponse().getAccounts()) {
				List<String> accountNumbers = new ArrayList<>();
				for (String custId : accountResponse.getAccountDetailResponse().getAccounts().keySet()) {
					for (Account acct : accountResponse.getAccountDetailResponse().getAccounts().get(custId)) {
						accountNumbers.add(acct.getAccountNumber());
					}
				}
			
				AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();
				accountTransactionRequest.setAccountNumbers(accountNumbers);
				HttpEntity transEntity = new HttpEntity(accountTransactionRequest, headers);
				ResponseEntity<ServiceResponse<TransactionsDetailResponse>> accountTransactionDetails = template
						.exchange(env.getProperty("transactionms.total-accounts"), HttpMethod.POST, transEntity,
								new ParameterizedTypeReference<ServiceResponse<TransactionsDetailResponse>>() {

								});
				if (accountTransactionDetails.getBody().getErrorInfo().getErrorCode().equals("00")) {
					accountResponse.setTransactionsDetailResponse(accountTransactionDetails.getBody().getData());

				} else {
					throw new GatewayException(accountTransactionDetails.getBody().getErrorInfo().getErrorCode(),
							accountTransactionDetails.getBody().getErrorInfo().getErrorMessage());
				}

			}
		} else {
			throw new GatewayException(accountOpenResponse.getBody().getErrorInfo().getErrorCode(),
					accountOpenResponse.getBody().getErrorInfo().getErrorMessage());
		}
		return accountResponse;
	}

}
