package com.twinkle.service;

import java.util.List;

import com.twinkle.common.model.Img;
import com.twinkle.task.ImgTask;
import com.twinkle.utils.CommUtils;

public class ImgService {

	public List<Img> getImg(int num, String types) {

		List<Img> list = Img.dao.find("select * from `img` where to_days(`date`) = to_days(now()) AND types = '"
				+ types + "' ORDER BY rand() LIMIT " + num);

		if (list.size() == 0) {
			
			CommUtils.INSTANCE.addToExeCutorPool(new ImgTask());
						
			return null;
		}

		return list;

	}


	public String getOneImg() {

		List<Img> list = Img.dao
				.find("select * from `img` where to_days(`date`) = to_days(now()) ORDER BY rand() LIMIT 1");
		
		if (list.size() == 0) {
			
			CommUtils.INSTANCE.addToExeCutorPool(new ImgTask());
			
			return null;
		}
		
		return list.get(0).getPic();
	}
		

}
