package com.textinca.dev.utilities;


import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;


import static com.textinca.dev.configs.SecurityConstants.TOKEN_PREFIX;
import static com.textinca.dev.configs.SecurityConstants.AUTHORIZATION_TYPE;
import static com.textinca.dev.configs.SecurityConstants.SECRET;

public class JwtUtil {

	protected final static String EMPTY_STRING = "";

    //This method validate the token sent from the client
    public static Authentication getAuthentication(HttpServletRequest request) 
    {
    	// We obtain the token contained into the request's header
        String token = request.getHeader(AUTHORIZATION_TYPE);
        UsernamePasswordAuthenticationToken authenticatedUser=null;
        // If the token exist, then we validate it
        if (canReachValidHeader(token)) { 
            String email = validateTokenAndGetEmail(token);
            if(canReachAnEmail(email))
            {
            	// We have to remind that all requests (different from login)
            	// does not need email/password authentication, for this reason
            	// we could return UsernamePasswordAuthenticationToken without password
            	authenticatedUser = new UsernamePasswordAuthenticationToken(
            								email, 
            								null, // password field
            								Collections.emptyList() // roles field
            							);
            }

        }
        return authenticatedUser;
    }
    
    public static String validateTokenAndGetEmail(String token)
    {
        String email = null;
        try
        {
        	// This do the validation and get the email if can validate
        	email = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, EMPTY_STRING)) //Remove "Bearer " header
                    .getBody()
                    .getSubject();
        }
        catch(Exception exception)
        {
        	System.out.println(exception);
        }
    	return email;
    }
    
	private static boolean canReachValidHeader(String requestedTokenHeader)
	{
		return requestedTokenHeader != null && requestedTokenHeader.startsWith(TOKEN_PREFIX);
	}
	
	private static boolean canReachAnEmail(String email)
	{
		return email!=null;
	}
}