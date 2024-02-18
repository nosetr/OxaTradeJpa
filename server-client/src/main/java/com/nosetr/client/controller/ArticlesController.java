package com.nosetr.client.controller;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Data access controller. We’ll use the previously configured WebClient to send
 * an HTTP request to our resource server:
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArticlesController {

	WebClient webClient;

	/**
	 * We’re taking the OAuth authorization token from the request in a form of
	 * OAuth2AuthorizedClient class. It’s automatically bound by Spring using the
	 * {@code @RegisterdOAuth2AuthorizedClient} annotation with proper
	 * identification. In our case, it’s pulled from the
	 * article-client-authorizaiton-code that we configured previously in the .yml
	 * file.
	 * <p>This authorization token is further passed to the HTTP request.
	 * 
	 * @autor                   Nikolay Osetrov
	 * @since                   0.1.4
	 * @param  authorizedClient
	 * @return
	 */
	@GetMapping(value = "/articles")
	public String[] getArticles(
			@RegisteredOAuth2AuthorizedClient("articles-client-authorization-code") OAuth2AuthorizedClient authorizedClient
	) {

		Consumer<Map<String, Object>> client = ServerOAuth2AuthorizedClientExchangeFilterFunction
				.oauth2AuthorizedClient(authorizedClient);

		return this.webClient
				.get()
				.uri("http://127.0.0.1:8090/articles")
				.attributes(client)
				.retrieve()
				.bodyToMono(String[].class)
				.block();
	}
}