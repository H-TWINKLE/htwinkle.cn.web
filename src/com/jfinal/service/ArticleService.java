package com.jfinal.service;

import java.util.List;

import com.jfinal.common.model.Article;
import com.twinkle.proxy.ArticleProxy;
import com.twinkle.task.ArticleTask;
import com.twinkle.utils.ExeCutorPool;

public class ArticleService {

	public Article getOneArticle() {

		Article article = Article.dao.findFirst("SELECT * FROM `article` ORDER BY rand() LIMIT 0,1");

		if (article == null) {
			return new ArticleProxy().getArtHtml();

		}
		return article;

	}
	
	
	public List<Article> getListArticle(Integer num){
		
		List<Article> list = Article.dao.find("SELECT * FROM `article` ORDER BY rand() LIMIT 0,?",num);
		
		if(list==null) {
			
		    ExeCutorPool.INSTANCE.AddToExeCutorPool(new ArticleTask()); 	    
		    return null;	    
		}
			
		
		return list;
						
	}
	
	
	

}
