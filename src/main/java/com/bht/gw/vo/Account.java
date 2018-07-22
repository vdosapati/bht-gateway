package com.bht.gw.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
public class Account {

	private String firstName;
	private String accountNumber;
	private String lastName;
	private String email;
	private String mobile;
	private String address;
	private String accountType;
	
}
