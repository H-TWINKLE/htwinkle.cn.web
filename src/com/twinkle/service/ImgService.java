package com.twinkle.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.twinkle.common.model.Img;
import com.twinkle.proxy.ImgProxy;
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

	public String addImg(String types) {

		return JSON.toJSONString(ImgProxy.INSTANCE.get3GBiZhiImg(types));
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

	public String checkParms(String types) {
		if (types == null || types.equals("")) {
			return types = "wallQFJ";
		}

		if (!"wallQFJ".equals(types) || !"wallKA".equals(types) || !"wallJZ".equals(types) || !"wallZW".equals(types)
				|| !"wallDW".equals(types))
			return types = "wallQFJ";

		return types;

	}
	
	

}
