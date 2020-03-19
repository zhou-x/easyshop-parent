package com.easyshop.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.easyshop.goods.service.SellerService;
import com.easyshop.pojo.Result;
import com.easyshop.pojo.Seller;

/**
 * <p>
 * 前端控制器
 * </p>
 * 
 * @author 陶毅
 */
@Controller
@RequestMapping("/seller")
public class SellerController {

	@Reference
	SellerService sellerService;

	/**
	 * 01-商家入驻
	 * @param seller
	 * @return
	 */
	@RequestMapping("/addseller")
	public String ruzhu(Seller seller) {
		seller.setStatus("0"); // 表示新入驻的商家 需要运营商审核 0
		seller.setDel(0); // 默认是未删除
		seller.setCreateTime(new Date()); // 入驻时间为当前系统时间
		
		//密码加密
	    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	    String password = passwordEncoder.encode(seller.getPassword()); //密文
	    seller.setPassword(password); //把加密之后的密文存起来  
	    
		boolean b = sellerService.insert(seller); //新增到数据库
		if (b) {
			return "ruzhu_success"; // 成功界面
		} else {
			return "ruzhu_fail"; // 失败界面
		}
	}
	

	@RequestMapping("/ckcode")
	@ResponseBody
	public Result ckcode(String usercode, HttpSession session) {
		String sygscode = (String) session.getAttribute("CODE"); // 系统生成的验证码
		if (sygscode.equalsIgnoreCase(usercode)) { // 验证码正确
			return new Result(true, "验证通过");
		} else {
			return new Result(false, "验证失败");
		}
	}
	
	/**
	 * 获取当前的登录人信息 
	 * @return
	 */
	@RequestMapping("/getName")
	@ResponseBody
	public Map<String,String> showName(){
		String name=SecurityContextHolder.getContext().getAuthentication().getName(); //当前登录的用户  
		Map<String,String> map=new HashMap<String,String>();
		map.put("loginName", name);
		return map ;
	}
	
}
