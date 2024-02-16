package com.nosetr.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Web security:
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Here we’ll need every request to be authenticated. Additionally, we need to
	 * configure the login page URL (defined in .yml config) and the OAuth client.
	 * 
	 * @autor            Nikolay Osetrov
	 * @since            0.1.4
	 * @param  http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(
						authorizeRequests -> authorizeRequests.anyRequest()
								.authenticated()
				)
				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/articles-client-oidc"))
				.oauth2Client(Customizer.withDefaults());
		return http.build();
	}
}