package com.test.session;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenHelper {
    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.header}")
    private String AUTH_HEADER;


    private String generateToken(String username) {
        try {
			return Jwts.builder()
			        .setIssuer( APP_NAME )
			        .setSubject(AUTH_HEADER)
			        .claim("name", username)
			        .signWith(
			        	    SignatureAlgorithm.HS256,
			        	    "secret".getBytes("UTF-8")
			        	  )
			         .setIssuedAt(generateCurrentDate())
			        .compact();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }
	
    private Date generateCurrentDate() {
        return new Date(DateTime.now().getMillis());
    }

    private String getToken(HttpServletRequest request, HttpServletResponse response) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = request.getHeader(AUTH_HEADER);
        if ( authHeader == null) {
            String userString = RandomStringUtils.randomAlphanumeric(10);
            String userToken = generateToken(userString);
            response.addHeader(AUTH_HEADER, userToken);

            return userToken;
        }
        else if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getUserName(HttpServletRequest request, HttpServletResponse response) {
    	String userToken = getToken(request, response);
    	
    	Jws<Claims> claims;
		try {
			claims = Jwts.parser()
					  .setSigningKey("secret".getBytes("UTF-8"))
					  .parseClaimsJws(userToken);
	    	return (String)claims.getBody().get("name");
	    	
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    

//	private ResourceEntity getUserState(HttpServletRequest request) {
//		ResourceEntity entity = (ResourceEntity) request.getSession().getAttribute("resourceEntity");
//
//		if (entity == null) {
//			entity = new ResourceEntity();
//			request.getSession().setAttribute("resourceEntity", entity);
//		}
//
//		return entity;
//	}

}
