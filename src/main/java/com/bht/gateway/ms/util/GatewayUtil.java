package com.bht.gateway.ms.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.bht.gw.vo.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class GatewayUtil {
	
	@Autowired
	private Environment env;
	
	public String createJwtToken(){
		Gson gson = new Gson();
		Token tokenVo = new Token();
		tokenVo.setSessionId("abc12345");
		String token = Jwts.builder().claim("token", gson.toJson(tokenVo)).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, env.getProperty("jwt.secret.key")).compact();
		 token = "Bearer" +token; 
		 return token;
	}

}
