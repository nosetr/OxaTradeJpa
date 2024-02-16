package com.nosetr.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@Configuration
public class WebClientConfig {

	/**
	 * WebClient instance to perform HTTP requests to our resource server. We’ll use
	 * the standard implementation with just one addition of the OAuth authorization
	 * filter:
	 * 
	 * @autor                          Nikolay Osetrov
	 * @since                          0.1.4
	 * @param  authorizedClientManager
	 * @return
	 */
	@Bean
	WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
				authorizedClientManager
		);
		return WebClient.builder()
				.apply(oauth2Client.oauth2Configuration())
				.build();
	}

	/**
	 * The WebClient requires an OAuth2AuthorizedClientManager as a dependency.
	 * Default implementation:
	 * 
	 * @autor                               Nikolay Osetrov
	 * @since                               0.1.4
	 * @param  clientRegistrationRepository
	 * @param  authorizedClientRepository
	 * @return
	 */
	@Bean
	OAuth2AuthorizedClientManager authorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository authorizedClientRepository
	) {

		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
				.authorizationCode()
				.refreshToken()
				.build();
		DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
				clientRegistrationRepository, authorizedClientRepository
		);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

		return authorizedClientManager;
	}
}
