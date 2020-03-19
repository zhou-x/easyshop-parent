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
import com.easyshop.goods.service.ContentCategoryService;
import com.easyshop.pojo.Content;
import com.easyshop.pojo.ContentCategory;
import com.easyshop.pojo.PageResult;
import com.easyshop.pojo.Result;

/**
 * <p>
 * 内容分类 前端控制器
 * </p>
 *
 * @author 陶毅
 */
@Controller
@RequestMapping("/contentCategory")
public class ContentCategoryController {

	@Reference
	ContentCategoryService contentCategoryService;

	// 查询首页
	@RequestMapping("/list/{pageIndex}")
	public String list(ContentCategory contentCategory, @PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model) {
		Page<ContentCategory> results = null;
		// 分页工具
		Page<ContentCategory> page = new Page<ContentCategory>(pageIndex, pageSize);
		EntityWrapper<ContentCategory> entityWrapper = new EntityWrapper<ContentCategory>();
		// 查询的数据结果集对象 查询条件2个
		if (contentCategory != null && contentCategory.getName() != null) {
			entityWrapper.like("name", contentCategory.getName());
		}
		entityWrapper.eq("del", 0);
		results = contentCategoryService.selectPage(page, entityWrapper);
		// 获取总数
		int totalCount = ((Long) results.getTotal()).intValue();
		// 查询是否有上一页
		boolean hasPrevious = results.hasPrevious();
		// 查询是否有下一页
		boolean hasNext = results.hasNext();
		// 查询到每页数据
		List<ContentCategory> contentCategorys = results.getRecords();
		// 查询出所有班级
		PageResult<ContentCategory> pageResult = new PageResult<ContentCategory>(totalCount, pageIndex, pageSize,
				contentCategorys, contentCategory);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("hasPrevious", hasPrevious);
		model.addAttribute("hasNext", hasNext);
		return "content_category"; // 跳转模板上
	}

	/**
	 * 增加
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(ContentCategory contentCategory) {
		contentCategory.setDel(0);
		System.out.println(contentCategory);
		try {
			contentCategoryService.insert(contentCategory);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	/**
	 * 获取实体
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	@ResponseBody
	public ContentCategory findOne(@PathVariable("id") Long id) {
		return contentCategoryService.selectById(id);
	}

	/**
	 * 修改
	 * 
	 * @param brand
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Result update(ContentCategory contentCategory) {
		System.out.println(contentCategory);
		try {
			boolean b = contentCategoryService.updateById(contentCategory);
			System.out.println(b);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	
	/**
	 * 03-根据ID删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result deleteContent(@PathVariable("id") Long id) {

		ContentCategory contentCategory = contentCategoryService.selectById(id);
		contentCategory.setDel(1);// 删除  --- 此处是假删除

		boolean b = contentCategoryService.updateById(contentCategory);
		Result result = null;
		if (b == true) {
			result = new Result(true, "删除成功");
		} else {
			result = new Result(false, "删除失败");
		}
		return result;
	}
}
