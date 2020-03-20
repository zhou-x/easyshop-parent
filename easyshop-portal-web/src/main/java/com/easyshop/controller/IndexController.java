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
		List<Content> lunBos = getLunBo();//查询轮播图
		List<Content> shengXians = getShengXian();//查询生鲜
		List<Content> shiPins = getShiPin();//查询食品
		model.addAttribute("shengXians", shengXians);
		model.addAttribute("lunBos", lunBos);	
		model.addAttribute("shiPins", shiPins);
		return "Index";
	}
	
	
	//查询轮播图
	public List<Content> getLunBo(){
		return contentService.selectList(new EntityWrapper<Content>().eq("del", 0).eq("status", 1).eq("category_id", 1));
	}
	
	
	//生鲜广告---查询数据库
		public List<Content> getShengXian(){
			return contentService.selectList(new EntityWrapper<Content>().eq("del", 0).eq("status", 1).eq("category_id", 7));
			//System.err.println("生鲜广告图数据来源于数据库......");
			//return shengxians;
		}
	
		
	//食品广告---查询数据库
			public List<Content> getShiPin(){
				return contentService.selectList(new EntityWrapper<Content>().eq("del", 0).eq("status", 1).eq("category_id", 8));
				//System.err.println("生鲜广告图数据来源于数据库......");
				//return shipins;
			}
}
