package com.nosetr.trade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that will return a list of articles under the GET /articles
 * endpoint:
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@RestController
public class ArticlesController {

	@GetMapping("/articles")
	public String[] getArticles() { return new String[] { "Article 1", "Article 2", "Article 3" }; }
}
