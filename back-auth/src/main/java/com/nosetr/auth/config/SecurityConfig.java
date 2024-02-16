package com.nosetr.auth.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
@Configuration
//@Order(1)
public class SecurityConfig {

	/**
	 * Filter chain to apply the default OAuth security and generate a default form
	 * login page:
	 * 
	 * @autor            Nikolay Osetrov
	 * @since            0.1.4
	 * @param  http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				.oidc(withDefaults()); // Enable OpenID Connect 1.0
		return http.formLogin(withDefaults())
				.build();
	}

	/**
	 * Second Spring Security filter chain for authentication:
	 * 
	 * @autor            Nikolay Osetrov
	 * @since            0.1.4
	 * @param  http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(2)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				// for all requests:
				authorizeRequests -> authorizeRequests.anyRequest()
						.authenticated()
		)
				.formLogin(withDefaults());
		return http.build();
	}

	/**
	 * set of example users that we’ll use for testing. For the sake of this
	 * example, we’ll create a repository with just a single admin user:
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	@Bean
	UserDetailsService users() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User.builder()
				.username("admin")
				.password("password")
				.passwordEncoder(encoder::encode)
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

}
