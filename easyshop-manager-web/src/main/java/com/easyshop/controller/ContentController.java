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
import com.easyshop.goods.service.ContentService;
import com.easyshop.pojo.Content;
import com.easyshop.pojo.ContentCategory;
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
@RequestMapping("/content")
public class ContentController {

	@Reference
	ContentService contentService;

	@Reference
	ContentCategoryService contentCategoryService;

	/**
	 * 01-查询首页
	 * 
	 * @param brand
	 * @param pageIndex
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("/list/{pageIndex}")
	public String list(Content content, @PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model) {
		Page<Content> results = null;
		// 分页工具
		Page<Content> page = new Page<Content>(pageIndex, pageSize);
		EntityWrapper<Content> entityWrapper = new EntityWrapper<Content>();
		// 查询的数据结果集对象 查询条件2个
		if (content != null && content.getTitle() != null) {
			entityWrapper.like("title", content.getTitle());
		}
		entityWrapper.eq("del", 0);
		results = contentService.selectPage(page, entityWrapper);
		// 获取总数
		int totalCount = ((Long) results.getTotal()).intValue();
		// 查询是否有上一页
		boolean hasPrevious = results.hasPrevious();
		// 查询是否有下一页
		boolean hasNext = results.hasNext();
		// 查询到每页数据
		List<Content> contents = results.getRecords();
		for (Content c : contents) {
			Long categoryId = c.getCategoryId(); // 分类ID
			ContentCategory contentCategory = contentCategoryService.selectById(categoryId);
			String name = contentCategory.getName(); // 分类名称
			c.setCategoryName(name);
		}

		// 查询出所有班级
		PageResult<Content> pageResult = new PageResult<Content>(totalCount, pageIndex, pageSize, contents, content);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("hasPrevious", hasPrevious);
		model.addAttribute("hasNext", hasNext);
		return "content"; // 跳转模板上
	}



	/**
	 * 02-增加
	 * 
	 * @param brand
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(Content content) {
		System.out.println(content);
		content.setDel(0);
		try {
			contentService.insert(content);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
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

		Content content = contentService.selectById(id);
		content.setDel(1);// 删除  --- 此处是假删除

		boolean b = contentService.updateById(content);
		Result result = null;
		if (b == true) {
			result = new Result(true, "删除成功");
		} else {
			result = new Result(false, "删除失败");
		}
		return result;
	}
	
	
	
	/**
	 * 03-根据status改变状态
	 * @param status
	 * @return
	 */
	@RequestMapping("/change/{id}")
	@ResponseBody
	public Result changeStatus(@PathVariable("id") Long id) {

		Content content = contentService.selectById(id);
		
		String str = content.getStatus();
		if(str.equals("0")){
			content.setStatus("1");
		}else{
			content.setStatus("0");
		}

		boolean b = contentService.updateById(content);
		Result result = null;
		if (b == true) {
			result = new Result(true, "删除成功");
		} else {
			result = new Result(false, "删除失败");
		}
		return result;
	}
	
	/**
	 * 04-查询广告类型
	 * @return
	 */
	@RequestMapping("/getContentCategorys")
	@ResponseBody
	public List<ContentCategory> getContentCategorys() {
		return contentCategoryService.selectList(new EntityWrapper<ContentCategory>().eq("del", 0));
	}
	
}
