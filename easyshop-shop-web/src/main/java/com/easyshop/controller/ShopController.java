package com.easyshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopController {

	@RequestMapping("/shop/index")
	public String tiaoIndex(){
		return "index";
	}
	
	@RequestMapping("/shop/home")
	public String tiaoHome(){
		return "home";
	}
}
