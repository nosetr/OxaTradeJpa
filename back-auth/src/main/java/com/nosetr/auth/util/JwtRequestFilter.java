package com.nosetr.auth.util;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nosetr.library.enums.ErrorEnum;
import com.nosetr.library.exception.AuthException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Set authentication.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 * @see   https://github.com/DarkSavant7/demo-market-app/blob/master/src/main/java/org/example/demomarketapp/util/JwtRequestFilter.java
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX = "Bearer ";
	private final JwtTokenUtil jwtTokenUtil;

	/**
	 * @autor                   Nikolay Osetrov
	 * @since                   0.1.4
	 * @param  request
	 * @param  response
	 * @param  filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
			String jwt = authHeader.substring(BEARER_PREFIX.length());
			try {
				String username = jwtTokenUtil.getUsernameFromToken(jwt);
				var token = new UsernamePasswordAuthenticationToken(
						username, null, jwtTokenUtil.getRolesFromToken(jwt)
								.stream()
								.map(SimpleGrantedAuthority::new)
								.toList()
				);
				SecurityContextHolder.getContext()
						.setAuthentication(token);
			} catch (ExpiredJwtException | MalformedJwtException e) {
				throw new AuthException(ErrorEnum.TOKEN_IS_EXPIRED);
			}
		}
		filterChain.doFilter(request, response);
	}

}
