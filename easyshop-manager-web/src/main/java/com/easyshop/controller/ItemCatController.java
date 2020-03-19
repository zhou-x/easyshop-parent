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
import com.easyshop.goods.service.ItemCatService;
import com.easyshop.goods.service.TypeTemplateService;
import com.easyshop.pojo.ItemCat;
import com.easyshop.pojo.PageResult;
import com.easyshop.pojo.Result;
import com.easyshop.pojo.TypeTemplate;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author 陶毅
 */
@Controller
@RequestMapping("/itemCat")
public class ItemCatController {

	@Reference
	ItemCatService itemCatService;

	@Reference
	TypeTemplateService typeTemplateService;

	// 查询首页
	@RequestMapping("/list/{pageIndex}")
	public String list(ItemCat itemCat, @PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model,
			@RequestParam(name = "parentId", defaultValue = "0") Long parentId) {
		Page<ItemCat> results = null;
		// 分页工具
		Page<ItemCat> page = new Page<ItemCat>(pageIndex, pageSize);
		EntityWrapper<ItemCat> entityWrapper = new EntityWrapper<ItemCat>();

		entityWrapper.eq("parent_id", parentId); // 默认查询1级分类
		entityWrapper.eq("del", 0);

		results = itemCatService.selectPage(page, entityWrapper);
		// 获取总数
		int totalCount = ((Long) results.getTotal()).intValue();
		// 查询是否有上一页
		boolean hasPrevious = results.hasPrevious();
		// 查询是否有下一页
		boolean hasNext = results.hasNext();
		// 查询到每页数据
		List<ItemCat> itemcats = results.getRecords();
		// 查询出所有班级
		PageResult<ItemCat> pageResult = new PageResult<ItemCat>(totalCount, pageIndex, pageSize, itemcats, itemCat);

		// ------------小算法 拼接导航菜单
		boolean flag = false; // 没有到三级菜单
		String str = "<li><a href=\"javascript:getChild(0);\">顶级分类列表</a></li>";
		if (parentId != 0) {
			ItemCat item1 = itemCatService.selectOne(new EntityWrapper<ItemCat>().eq("id", parentId));// 根据parentId查询下一级
			if (item1 != null) {
				String name = item1.getName();
				String str1 = "<li><a href=\"javascript:getChild(" + item1.getId() + ");\">" + name + "</a></li>";
				Long p2 = item1.getParentId();
				ItemCat item2 = itemCatService.selectOne(new EntityWrapper<ItemCat>().eq("id", p2));
				if (item2 != null) {
					String name2 = item2.getName();
					str = str + "<li><a href=\"javascript:getChild(" + item2.getId() + ");\">" + name2 + "</a></li>";
					flag = true; // 表示已经到三级菜单
				}
				str = str + str1;
			}
		}
		System.err.println(str);
		// ------------小算法 拼接导航菜单

		model.addAttribute("flag", flag); // 存储是否是三级菜单
		model.addAttribute("str", str); // 存储菜单
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("hasPrevious", hasPrevious);
		model.addAttribute("hasNext", hasNext);
		model.addAttribute("parentId", parentId);
		return "item_cat"; // 跳转模板上
	}

	/**
	 * 02-查询所有的模板用作下拉框显示
	 */
	@ResponseBody
	@RequestMapping("/getType_templates")
	public List<TypeTemplate> getType_templates() {
		EntityWrapper<TypeTemplate> entityWrapper = new EntityWrapper<TypeTemplate>();
		entityWrapper.eq("del", 0);// 未删除的模板
		List<TypeTemplate> typeTemplates = typeTemplateService.selectList(entityWrapper);
		return typeTemplates;
	}

	/**
	 * 03-新增类目
	 * @param itemCat
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Result add(ItemCat itemCat) {
		itemCat.setDel(0);//不删除
		System.out.println(itemCat);
		try {
			itemCatService.insert(itemCat);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	
	/**
	 * 04-根据ID查询套回显的对象
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	@ResponseBody
	public ItemCat findOne(@PathVariable("id") Long id) {
		return itemCatService.selectById(id);
	}
	
	
	/**
	 * 05-更新类目
	 * @param itemCat
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Result update(ItemCat itemCat){
		System.out.println(itemCat);
		try {
			itemCatService.updateById(itemCat);
			return new Result(true, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "更新失败");
		}
	}
	
	/**
	 * 06-删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result deleteItemCat(@PathVariable("id") Long id) {

		ItemCat itemCat = itemCatService.selectById(id);
		itemCat.setDel(1);// 删除  --- 此处是假删除

		boolean b = itemCatService.updateById(itemCat);
		Result result = null;
		if (b == true) {
			result = new Result(true, "删除成功");
		} else {
			result = new Result(false, "删除失败");
		}
		return result;
	}

}
