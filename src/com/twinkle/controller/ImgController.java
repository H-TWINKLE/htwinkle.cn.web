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

	public void img_api() {
		renderJson(getImg());
	}

	public void addimg_api() {

		String types = getPara("types");
		types = imgService.checkParms(types);
		renderJson(imgService.addImg(types));

	}

	@Override
	public void api() {
		redirect(imgService.getOneImg());
	}

	private List<Img> getImg() {
		Integer num = getParaToInt("num");
		if (num == null || num == 0) {
			num = 10;
		}
		String types = getPara("types");
		types = imgService.checkParms(types);
		return imgService.getImg(num, types);
	}

}
