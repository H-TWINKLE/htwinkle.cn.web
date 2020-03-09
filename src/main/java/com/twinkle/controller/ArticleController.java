package com.twinkle.controller;

import com.twinkle.service.ArticleService;
import com.twinkle.utils.BaseController;

public class ArticleController extends BaseController {

	private final static ArticleService service = new ArticleService();

	@Override
	public void index() {

		setAttr("article", service.getOneArticle());
		render("/pages/page_article.html");

	}

	@Override
	public void api() {
		
		Integer num = getParaToInt("num");

		if (num == null || num == 0) {
			num = 10;
		}

		renderJson(service.getListArticle(num));

	}

}
