package com.bht.gw.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.google.gson.Gson;

import antlr.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthConfig {
	
	@Autowired
	private Environment env;
	
	
	public String createJwtToken(Token tokenVo){
		Gson gson = new Gson();
		String token = Jwts.builder().claim("token", gson.toJson(tokenVo)).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, env.getProperty("jwt.secret.key")).compact();
		return token = "Bearer" +token; 
	}

}
