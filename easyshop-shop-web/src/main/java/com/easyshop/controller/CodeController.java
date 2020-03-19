package com.easyshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easyshop.utils.VerifyCodeUtils;

@Controller
public class CodeController {
	

	/*
	 * 生成一张随机的验证码图片
	 */
	@RequestMapping("/createcode")
	public void createCode(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

		// 存入会话session  系统生成的验证码存入session
		req.getSession().setAttribute("CODE", verifyCode.toLowerCase());

		// 生成图片
		int width = 100;// 宽
		int height = 40;// 高
		
		VerifyCodeUtils.outputImage(width, height, resp.getOutputStream(), verifyCode);
	}
}
