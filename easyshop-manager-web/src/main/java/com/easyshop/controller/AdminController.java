package com.easyshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	@RequestMapping("/admin/index")
	public String goIndex(){
		return "index"; // 跳转index  首页
	}
	
	@RequestMapping("/admin/main")
	public String goMain(){
		return "main"; // 跳转main
	}
}
