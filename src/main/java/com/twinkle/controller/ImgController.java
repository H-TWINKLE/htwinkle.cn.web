package com.twinkle.controller;

import java.util.List;

import com.twinkle.common.model.Img;
import com.twinkle.service.ImgService;
import com.twinkle.utils.BaseController;

public class ImgController extends BaseController {

	static ImgService imgService = new ImgService();

	public void index() {

		setAttr("img", getImg());
		setAttr("title", "图片瀑布");
		render("/pages/page_img.html");

	}

	@Override
	public void api() {

		Integer num = getParaToInt("num");

		if (num == null || num == 0) {
			redirect(imgService.getOneImg());
			return;
		}

		renderJson(getImg());

	}

	private List<Img> getImg() {
		
		return imgService.getImg(getNum(), checkParmsByTypes());
		
	}

	private Integer getNum() {

		Integer num = getParaToInt("num");
		if (num == null || num == 0) {
			return 10;
		}

		return num;

	}

	private String checkParmsByTypes() {

		String types = getPara("types");

		if (types == null || "".equals(types)) {
			return types = "fengjing";
		}

		if (!"fengjing".equals(types) || !"keai".equals(types) || !"jianzhu".equals(types) || !"zhiwu".equals(types)
				|| !"dongwu".equals(types))
			return types = "fengjing";

		return types;

	}

}
