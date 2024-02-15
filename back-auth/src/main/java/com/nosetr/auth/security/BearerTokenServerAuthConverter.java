package com.nosetr.auth.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nosetr.auth.security.JwtHandler.VerificationResult;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Set authentication.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
@RequiredArgsConstructor
public class BearerTokenServerAuthConverter extends OncePerRequestFilter {

	private final JwtHandler jwtHandler;

	private static final String BEARER_PREFIX = "Bearer ";

	/**
	 * @autor                   Nikolay Osetrov
	 * @since                   0.1.4
	 * @param  request
	 * @param  response
	 * @param  filterChain
	 * @throws ServletException
	 * @throws IOException
	 * @see                     https://github.com/DarkSavant7/demo-market-app/blob/master/src/main/java/org/example/demomarketapp/util/JwtRequestFilter.java
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
			// Get value of bearer token:
			String jwt = authHeader.substring(BEARER_PREFIX.length());
			// get VerificationResult or error exception:
			VerificationResult check = jwtHandler.check(jwt);
			Authentication authentication = UserAuthenticationBearer.create(check);
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);

	}
}
