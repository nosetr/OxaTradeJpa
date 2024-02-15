package com.nosetr.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nosetr.auth.util.JwtRequestFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom configuration for spring-security
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 * @see   https://github.com/DarkSavant7/demo-market-app/blob/master/src/main/java/org/example/demomarketapp/config/SecurityConfig.java
 */
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
//@Order(1)
public class SecurityConfig {

	/*
	 * Array of routes with public access for registration and login
	 */
	private final String[] publicRoutes = {
			"/api/v1/auth/register",
			"/api/v1/auth/login",
			"/login/**",
			"/api/v1/newsletter",
			"/error",
			"/favicon.ico"
	};

	/*
	 * Special routes for swagger
	 */
	private final String[] swaggerRouteStrings = {
			"/swagger",
			"/api-docs/**",
			"/swagger-ui/**"
	};

	/*
	 * Array of routes with access for users with role "USER"
	 */
	private final String[] usersRoutes = { "/api/v1/secure/**" };

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			JwtRequestFilter jwtRequestFilter
	)
			throws Exception {

		return http
				// This configuration disables CSRF protection and allows all requests to access
				// resources without requiring authorization or authentication, thus eliminating
				// the need for a password (should be enabled in production):
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						auth -> auth
								.requestMatchers(publicRoutes) // make routes from publicRoutes public
								.permitAll()
								.requestMatchers(swaggerRouteStrings) // swagger routes
								.permitAll()
								.requestMatchers(usersRoutes)
								.hasAnyRole("USER", "ADMIN", "SUPERADMIN")
								.anyRequest() // all other routes are not public
								.authenticated()
				)
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				// sample exception handling customization
				.exceptionHandling(
						exceptionHandling -> exceptionHandling
								// customize how to request for authentication
								.authenticationEntryPoint((request, response, accessDeniedException) -> {
									log.error(
											"IN securityWebFilterChain - unauthorized error: {}, RequestURL: {}", accessDeniedException
													.getMessage(), request.getRequestURL()
									);
									response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
								})
								.accessDeniedHandler((request, response, accessDeniedException) -> {
									log.error("IN securityWebFilterChain - access denied: {}", accessDeniedException.getMessage());
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
								})
				)
				.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration
	)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
