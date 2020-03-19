package com.easyshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.easyshop.goods.service.SellerService;
import com.easyshop.pojo.Seller;

/*
 * 商家的信息认证--给Springsecurity使用
 * 完成用户的账号和密码的自动校验 --->连接真实的数据库
 */
public class UserAuthorization implements UserDetailsService {

	SellerService sellerService;

	// 选中在配置文件 使用set注入的方式 注入sellerService对象
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	// 自动去查询数据库，完成密码的校验 username就是登录界面中传过来的username
//	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("用户开始登录，输入的账号是:"+username);
		
		// 构建角色列表
		List<GrantedAuthority> grantAuths = new ArrayList();
		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));

		// 根据账号查一个对象
		Seller seller = sellerService.selectOne(new EntityWrapper<Seller>().eq("seller_id", username));
		
		if (seller != null) {
			//审核通过才能登录
			if(seller.getStatus().equals("1")){   
				System.out.println("用户登录成功:"+seller);
				return new User(username,seller.getPassword(),grantAuths);  //创建一个对象
				
			}else{
				return null;
			}	
		} else {
			return null;
		}
	}

}
