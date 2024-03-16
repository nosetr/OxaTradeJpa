package com.nosetr.trade.config;

/**
 * Set up our web security configuration.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
//@Configuration
//@EnableWebSecurity
public class ResourceServerConfig {

	/**
	 * we want to explicitly state that every request to article resources should be
	 * authorized and have the proper articles.read authority.
	 * <p>We’re also invoking the oauth2ResourceServer() method, which will
	 * configure the OAuth server connection based on the application.yml
	 * configuration:
	 * 
	 * @autor            Nikolay Osetrov
	 * @since            0.1.4
	 * @param  http
	 * @return
	 * @throws Exception
	 */
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.securityMatcher("/articles/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest()
//								.hasAuthority("SCOPE_articles.read")
//				)
//				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//		return http.build();
//	}
}
