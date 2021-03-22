package com.textinca.dev.models;

import static com.textinca.dev.configs.SecurityConstants.SECRET;
import static com.textinca.dev.configs.SecurityConstants.TOKEN_PREFIX;

import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtObject {
	
	
	private String email;
	private Date expirationDate;
	private String token;
	private Claims claims;
	
	protected final static String EMPTY_STRING = "";
	
	
	public JwtObject(String requestedTokenHeader)
	{
		buildFromTokenHeader(requestedTokenHeader);
	}
	
	private void buildFromTokenHeader(String requestedTokenHeader)
	{
		if(hasValidHeader(requestedTokenHeader))
		{
			this.token = requestedTokenHeader.replace(TOKEN_PREFIX, EMPTY_STRING);
			this.claims = getAllClaimsFromToken(token);
			this.setEmail(token);
			this.setExpirationDate(requestedTokenHeader);
		}
	}
	
	private boolean hasValidHeader(String requestedTokenHeader)
	{
		return requestedTokenHeader != null && requestedTokenHeader.startsWith(TOKEN_PREFIX);
	}
	
    private void setEmail(String token)
    {
    	this.email = getClaimFromToken(token, Claims::getSubject);
    }
    
    private void setExpirationDate(String token)
    {
    	this.expirationDate = getClaimFromToken(token, Claims::getExpiration);
    }
    
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		return claimsResolver.apply(this.claims);
	}
	
	//for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}
	
	public boolean hasEmail(String email)
	{
		return this.email!=null;
	}
	
	public boolean isTokenExpired(String token) {
		return expirationDate.before(new Date());
	}
	
	public String getEmail()
	{
		return this.email;
	}

	public String getToken() {
		return token;
	}
}
