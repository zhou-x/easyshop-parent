package com.easyshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.easyshop.goods.service.ContentService;
import com.easyshop.pojo.Content;

@Controller
public class IndexController {
	
	@Reference
	ContentService contentService;

	@RequestMapping({ "/index", "/index.html", "/" })
	public String index(Model model) {
		List<Content> lunBos = getLunBo();
		model.addAttribute("lunBos", lunBos);
		//查询轮播图
		return "Index";
	}
	
	public List<Content> getLunBo(){
		return contentService.selectList(new EntityWrapper<Content>().eq("del", 0).eq("status", 1).eq("category_id", 1));
	}
}
