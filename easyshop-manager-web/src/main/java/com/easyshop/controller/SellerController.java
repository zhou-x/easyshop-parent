package com.easyshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.easyshop.goods.service.SellerService;
import com.easyshop.pojo.PageResult;
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
	 * 01-查询分页 待审核的商家  
	 * @return
	 */
	// 查询首页
	@RequestMapping("/list/{pageIndex}")
	public String list(Seller seller, @PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model) {
		
		Page<Seller> results = null;
		// 分页工具
		Page<Seller> page = new Page<Seller>(pageIndex, pageSize);
		EntityWrapper<Seller> entityWrapper = new EntityWrapper<Seller>();
		
		//公司名查询
		if (seller != null && seller.getName() != null) {
			entityWrapper.like("name", seller.getName());
		}
		
		// 店铺名字  查询条件2个
		if (seller != null && seller.getNickName() != null) {
			entityWrapper.like("nick_name", seller.getNickName());
		}
		entityWrapper.eq("status", 0);  //待审核的店铺查询   status=0
		entityWrapper.eq("del", 0);
		
		
		results = sellerService.selectPage(page, entityWrapper);
		// 获取总数
		int totalCount = ((Long) results.getTotal()).intValue();
		// 查询是否有上一页
		boolean hasPrevious = results.hasPrevious();
		// 查询是否有下一页
		boolean hasNext = results.hasNext();
		// 查询到每页数据
		List<Seller> sellers = results.getRecords();
		// 查询出所有班级
		PageResult<Seller> pageResult = new PageResult<Seller>(totalCount, pageIndex, pageSize, sellers, seller);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("hasPrevious", hasPrevious);
		model.addAttribute("hasNext", hasNext);
		return "seller"; // 跳转模板上
	}
	
	
	/**
	 * 02-根据ID查询一个对象
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	@ResponseBody
	public Seller findOne(@PathVariable("id") Integer id){
		return sellerService.selectById(id);
	}
	
	
	/**
	 * 03-商家审核
	 * @param seller
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateStatus")
	public Result updateStatus(String status,Integer id) {
		Seller seller=new Seller();
		seller.setId(id);
		seller.setStatus(status);
		System.err.println(seller);
		
		try {
			boolean b = sellerService.updateById(seller);
			System.out.println(b);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
}
