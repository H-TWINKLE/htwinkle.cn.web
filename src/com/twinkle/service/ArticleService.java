package com.twinkle.service;

import java.util.List;

import com.twinkle.common.model.Article;
import com.twinkle.proxy.ArticleProxy;
import com.twinkle.task.ArticleTask;
import com.twinkle.utils.CommUtils;

public class ArticleService {

	public Article getOneArticle() {

		Article article = Article.dao.findFirst("SELECT * FROM `article` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `article`)-(SELECT MIN(id) FROM `article`))+(SELECT MIN(id) FROM `article`)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id LIMIT 1");

		if (article == null) {
			return new ArticleProxy().getArtHtml();

		}
		return article;

	}
	
	
	public List<Article> getListArticle(Integer num){
		
		List<Article> list = Article.dao.find("SELECT * FROM `article` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `article`)-(SELECT MIN(id) FROM `article`))+(SELECT MIN(id) FROM `article`)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id LIMIT 0,?",num);
		
		if(list==null) {
			
		    CommUtils.INSTANCE.addToExeCutorPool(new ArticleTask()); 	    
		    return null;	    
		}
			
		
		return list;
						
	}
	
	
	

}
