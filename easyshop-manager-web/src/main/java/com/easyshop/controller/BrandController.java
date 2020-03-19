package com.easyshop.controller;

import java.util.Arrays;
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
import com.easyshop.goods.service.BrandService;
import com.easyshop.pojo.Brand;
import com.easyshop.pojo.PageResult;
import com.easyshop.pojo.Result;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陶毅
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

	@Reference
	BrandService brandService;

	/*
	 * 测试用的
	 */
	@RequestMapping("/findAll")
	public String list(Model model) {
		List<Brand> brands = brandService.selectList(null);
		model.addAttribute("brands", brands);
		return "brand"; // 跳转 模板的页面 /WEB-INF/templates/brand.html
	}

	/**
	 * 01-分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/list/{pageIndex}")
	public String list(Brand brand, @PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model) {
		Page<Brand> results = null; // 分页的参数
		// 分页工具
		Page<Brand> page = new Page<Brand>(pageIndex, pageSize);

		EntityWrapper<Brand> entityWrapper = new EntityWrapper<Brand>();

		// 查询的条件 根据品牌的名字模糊查询 like
		if (brand != null && brand.getName() != null) {
			entityWrapper.like("name", brand.getName());
		}
		entityWrapper.eq("del", 0);// 查询未删除 如果删除了就不查询出来 0表示未删除 1表示删除 (数据库并非是真删除而是作逻辑删除)
		entityWrapper.orderBy("id").last(" desc");

		results = brandService.selectPage(page, entityWrapper);
		// 获取总数
		int totalCount = ((Long) results.getTotal()).intValue();
		// 查询是否有上一页
		boolean hasPrevious = results.hasPrevious();
		// 查询是否有下一页
		boolean hasNext = results.hasNext();
		// 查询到每页数据
		List<Brand> brands = results.getRecords();
		// 查询出所有班级
		PageResult<Brand> pageResult = new PageResult<Brand>(totalCount, pageIndex, pageSize, brands, brand);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("hasPrevious", hasPrevious);
		model.addAttribute("hasNext", hasNext);
		// System.err.println(pageResult);
		return "brand"; // 跳转模板上
	}

	/**
	 * 02-新增品牌
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(Brand brand) {
		brand.setDel(0);// 未删除
		boolean b = brandService.insert(brand);
		Result result = null;
		if (b == true) {
			result = new Result(true, "新增成功");
		} else {
			result = new Result(false, "新增失败");
		}
		return result;
	}

	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result deleteBrand(@PathVariable("id") Long id) {

		Brand brand = brandService.selectById(id);
		brand.setDel(1);// 删除  --- 此处是假删除

		boolean b = brandService.updateById(brand);
		Result result = null;
		if (b == true) {
			result = new Result(true, "删除成功");
		} else {
			result = new Result(false, "删除失败");
		}
		return result;
	}
	
	
	/**
	 * 04-根据ID查询对象
	 */
	@RequestMapping("/getById/{id}")
	@ResponseBody
	public Brand getById(@PathVariable("id") Long id){
		return brandService.selectById(id);
	}
	
	/**
	 * 05-更新品牌
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result updateBrand(Brand brand){
		boolean b = brandService.updateById(brand);
		Result result = null;
		if (b == true) {
			result = new Result(true, "更新成功");
		} else {
			result = new Result(false, "更新失败");
		}
		return result;
	}
	
	/**
	 * 06-批量删除
	 * @return
	 */
	@RequestMapping("/deleteSome")
	@ResponseBody
	public Result deleteSome(Integer[] ids){
		//34,56,89   ----------> update brand set del=1 where id in ("+ids+")
		System.out.println(Arrays.toString(ids));
		int count = brandService.deleteSome(ids);
		Result result = null;
		if (count>0) {
			result = new Result(true, "删除成功");
		} else {
			result = new Result(false, "删除失败");
		}
		return result;
	}

	
}
