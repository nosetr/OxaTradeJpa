package com.nosetr.trade.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Service
class GreetingService {

	@PreAuthorize("hasAuthority('SCOPE_user.read')")
	public Map<String, String> greet() {
		var jwt = (Jwt) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
		return Map.of("message", "hello " + jwt.getSubject());
	}
}

/**
 * REST controller that will return a list of articles under the GET /articles
 * endpoint:
 * <p><b>From terminal to test:</b>
 * <p>Take a new token:
 * <p>{@code export TOKEN=http -f POST http://auth-server:9090/oauth2/token grant_type=client_credentials scope='user.read' -a admin-client:secret | jq -r ".access_token" }
 * <p>Request with token to resource server:
 * <p>{@code curl -H"authorization: bearer $TOKEN" http://localhost:8090/hello }
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@EnableMethodSecurity
@RestController
@RequiredArgsConstructor
public class ArticlesController {

	private final GreetingService greetingService;

	@GetMapping("/articles")
	public String[] getArticles() { return new String[] { "Article 1", "Article 2", "Article 3" }; }

	@GetMapping("/hello")
	public Map<String, String> hello(@AuthenticationPrincipal Jwt jwt) {

		return this.greetingService.greet();
	}
}
