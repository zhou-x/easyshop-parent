package com.easyshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 仅仅测试用的
 */
@Controller
public class HelloController {

	@RequestMapping("/say")
	public String hello(Model model) {
		String name = "Hello哈哈哈";
		model.addAttribute("name", name);
		model.addAttribute("age", 18);
		System.out.println(name);
		return "hello";  //此时返回的是一个模板页面  hello.html 这个页面必须位于  /WEB-INF/templates/
	}
}
