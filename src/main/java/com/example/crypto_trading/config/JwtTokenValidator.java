package com.example.crypto_trading.config;

import java.io.IOException;
import java.security.KeyStore.SecretKeyEntry;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator  extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jwt = request.getHeader(JwtConstants.JWT_HEADER);
		
		if(jwt != null) {
			jwt.substring(7);
			try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

				
				Claims claims = Jwts.parser().verifyWith(key).build()
				        .parseSignedClaims(jwt)
				        .getPayload();
				String  email	= String.valueOf(claims.get("email"));
				String  authorities	= String.valueOf(claims.get("authorities"));
				
				List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(
						email,
						null,
						authoritiesList);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException("Invalid Token....");
			}
		}
		filterChain.doFilter(request, response);
	}

}
