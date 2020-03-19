package com.easyshop.controller;

import java.util.Arrays;
import java.util.HashMap;
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
import com.easyshop.goods.service.SpecificationOptionService;
import com.easyshop.goods.service.SpecificationService;
import com.easyshop.pojo.PageResult;
import com.easyshop.pojo.Result;
import com.easyshop.pojo.Specification;
import com.easyshop.pojo.SpecificationOption;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陶毅
 */
@Controller
@RequestMapping("/specification")
public class SpecificationController {

	@Reference
	SpecificationService specificationService;
	
	@Reference
	SpecificationOptionService specificationOptionService;

	/**
	 * 01-分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/list/{pageIndex}")
	public String list(Specification specification, @PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model) {
		Page<Specification> results = null; // 分页的参数
		// 分页工具
		Page<Specification> page = new Page<Specification>(pageIndex, pageSize);

		EntityWrapper<Specification> entityWrapper = new EntityWrapper<Specification>();

		// 查询的条件 根据品牌的名字模糊查询 like
		if (specification != null && specification.getSpecName() != null) {
			entityWrapper.like("spec_name", specification.getSpecName());
			// System.out.println("搜索条件:"+ specification.getSpecName());
		}
		entityWrapper.eq("del", 0);// 查询未删除 如果删除了就不查询出来 0表示未删除 1表示删除
									// (数据库并非是真删除而是作逻辑删除)
		entityWrapper.orderBy("id").last(" desc");

		results = specificationService.selectPage(page, entityWrapper);
		// 获取总数
		int totalCount = ((Long) results.getTotal()).intValue();
		// 查询是否有上一页
		boolean hasPrevious = results.hasPrevious();
		// 查询是否有下一页
		boolean hasNext = results.hasNext();
		// 查询到每页数据
		List<Specification> specifications = results.getRecords();
		// 查询出所有班级
		PageResult<Specification> pageResult = new PageResult<Specification>(totalCount, pageIndex, pageSize,
				specifications, specification);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("hasPrevious", hasPrevious);
		model.addAttribute("hasNext", hasNext);
		// System.err.println(pageResult);
		return "specification"; // 跳转模板上
	}

	/**
	 * 02-新增
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(Specification specification, String[] optionName, Integer[] orders) {
		System.out.println(specification);
		System.out.println(Arrays.toString(optionName));
		System.out.println(Arrays.toString(orders));
		// 新增数据到数据库
		// 需要手动的去service开事务
		try {
			specificationService.insertSpecification(specification, optionName, orders);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 03-根据ID查询
	 */
	@RequestMapping("/findOne/{id}")
	@ResponseBody
	public HashMap<String, Object> findOne(@PathVariable("id") Integer id){
		HashMap<String, Object> map=new HashMap<String, Object>();
		//规格表
		Specification specification = specificationService.selectById(id); 
		//规格选项
		List<SpecificationOption> specificationoptions = specificationOptionService.selectList(new EntityWrapper<SpecificationOption>().eq("spec_id", specification.getId()).eq("del", 0));
		map.put("specification", specification);
		map.put("specificationoptions", specificationoptions);
		return map;
	}
	
	/**
	 * 04-更新
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(Specification specification, String[] optionName, Integer[] orders) {
		System.out.println(specification);
		System.out.println(Arrays.toString(optionName));
		System.out.println(Arrays.toString(orders));
		// 新增数据到数据库
		// 需要手动的去service开事务
		try {
			specificationService.updatetSpecification(specification, optionName, orders);
			return new Result(true, "更新成功");
		} catch (Exception e) {
			return new Result(false, "更新失败");
		}
	}
	
	/**
	 * 05-批量删除
	 * @return
	 */
	@RequestMapping("/deleteSome")
	@ResponseBody
	public Result deleteSome(Integer[] ids){
	//	System.out.println(Arrays.toString(ids));
		int count = specificationService.deleteSome(ids);
		Result result = null;
		if (count>0) {
			result = new Result(true, "删除成功");
		} else {
			result = new Result(false, "删除失败");
		}
		return result;
	}

	
}
