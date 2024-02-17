package com.nosetr.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
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
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Beans configuration
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${oxa.cors.max-age-secs}")
	private long MAX_AGE_SECS;

	@Value("${oxa.cors.allowed-origins}")
	private List<String> allowedOrigins;

	/**
	 * Spring Security filter chain to apply the default OAuth security and generate
	 * a default form login page:
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
				.oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0
		http
				// Redirect to the login page when not authenticated from the
				// authorization endpoint
				.exceptionHandling(
						(exceptions) -> exceptions
								.defaultAuthenticationEntryPointFor(
										new LoginUrlAuthenticationEntryPoint("/login"), new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
								)
				)
				// Accept access tokens for User Info and/or Client Registration
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

		return http.cors(Customizer.withDefaults())
				.build();
	}

	/**
	 * The second Spring Security filter chain for authentication.
	 * <p>Here we’re calling authorizeRequests.anyRequest().authenticated() to
	 * require
	 * authentication for all requests. We’re also providing a form-based
	 * authentication by invoking the formLogin(defaults()) method.
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
		http
				// This configuration disables CSRF protection and allows all requests to access
				// resources without requiring authorization or authentication, thus eliminating
				// the need for a password (should be enabled in production):
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						authorizeRequests -> authorizeRequests.anyRequest()
								.authenticated()
				)
				.formLogin(Customizer.withDefaults());
		return http.cors(Customizer.withDefaults())
				.build();
	}

	/**
	 * Finally, we’ll define a set of example users that we’ll use for testing. For
	 * the sake of this example, we’ll create a repository with just a single admin
	 * user:
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

	/**
	 * Enabling Cross Origin Requests for a RESTful Web Service.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setAllowedOrigins(allowedOrigins);
		config.setAllowCredentials(true);
		config.setMaxAge(MAX_AGE_SECS);
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	/**
	 * An instance of RegisteredClientRepository for managing clients.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	//	@Bean
	//	public RegisteredClientRepository registeredClientRepository() {
	//		RegisteredClient oidcClient = RegisteredClient.withId(
	//				UUID.randomUUID()
	//						.toString()
	//		)
	//				.clientId("oidc-client")
	//				.clientSecret("{noop}secret")
	//				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	//				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	//				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
	//				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
	//				.postLogoutRedirectUri("http://127.0.0.1:8080/")
	//				.scope(OidcScopes.OPENID)
	//				.scope(OidcScopes.PROFILE)
	//				.clientSettings(
	//						ClientSettings.builder()
	//								.requireAuthorizationConsent(true)
	//								.build()
	//				)
	//				.build();
	//
	//		return new InMemoryRegisteredClientRepository(oidcClient);
	//	}

	/**
	 * An instance of com.nimbusds.jose.jwk.source.JWKSource for signing access
	 * tokens.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	//	@Bean
	//	public JWKSource<SecurityContext> jwkSource() {
	//		KeyPair keyPair = generateRsaKey();
	//		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
	//		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
	//		RSAKey rsaKey = new RSAKey.Builder(publicKey)
	//				.privateKey(privateKey)
	//				.keyID(
	//						UUID.randomUUID()
	//								.toString()
	//				)
	//				.build();
	//		JWKSet jwkSet = new JWKSet(rsaKey);
	//		return new ImmutableJWKSet<>(jwkSet);
	//	}

	/**
	 * An instance of java.security.KeyPair with keys generated on startup used to
	 * create the JWKSource above.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	//	private static KeyPair generateRsaKey() {
	//		KeyPair keyPair;
	//		try {
	//			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	//			keyPairGenerator.initialize(2048);
	//			keyPair = keyPairGenerator.generateKeyPair();
	//		} catch (Exception ex) {
	//			throw new IllegalStateException(ex);
	//		}
	//		return keyPair;
	//	}

	/**
	 * An instance of JwtDecoder for decoding signed access tokens.
	 * 
	 * @autor            Nikolay Osetrov
	 * @since            0.1.4
	 * @param  jwkSource
	 * @return
	 */
	//	@Bean
	//	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
	//		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	//	}

	/**
	 * An instance of AuthorizationServerSettings to configure Spring Authorization
	 * Server.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	//	@Bean
	//	public AuthorizationServerSettings authorizationServerSettings() {
	//		return AuthorizationServerSettings.builder()
	//				.build();
	//	}
}
