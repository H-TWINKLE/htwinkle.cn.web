package com.jfinal.controller;

import com.alibaba.fastjson.JSON;
import com.jfinal.service.ArticleService;
import com.twinkle.init.BaseController;

public class ArticleController extends BaseController {

	static ArticleService service = new ArticleService();

	@Override
	public void index() {

		setAttr("article", service.getOneArticle());
		render("/pages/page_article.html");

	}

	@Override
	public void api() {
		Integer num = getParaToInt("num");

		if (num == null || num == 0) {
			num = 10;}
		
		renderJson(JSON.toJSONString(service.getListArticle(num)));

	}

}
