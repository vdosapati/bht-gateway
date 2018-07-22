package com.bht.gw.vo;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDetailResponse {
	private List<Account> accountDetails;
	private Map<String, List<Account>> accounts;
}
