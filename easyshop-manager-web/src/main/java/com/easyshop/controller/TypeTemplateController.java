package com.easyshop.controller;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.easyshop.goods.service.BrandService;
import com.easyshop.goods.service.SpecificationService;
import com.easyshop.goods.service.TypeTemplateService;
import com.easyshop.pojo.Brand;
import com.easyshop.pojo.JsonObject;
import com.easyshop.pojo.PageResult;
import com.easyshop.pojo.Result;
import com.easyshop.pojo.Specification;
import com.easyshop.pojo.TypeTemplate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陶毅
 */
@Controller
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	TypeTemplateService typeTemplateService;

	@Reference
	BrandService brandService;

	@Reference
	SpecificationService specificationService;

	/**
	 * 01-分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/list/{pageIndex}")
	public String list(TypeTemplate typeTemplate, @PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model) {
		Page<TypeTemplate> results = null; // 分页的参数
		// 分页工具
		Page<TypeTemplate> page = new Page<TypeTemplate>(pageIndex, pageSize);

		EntityWrapper<TypeTemplate> entityWrapper = new EntityWrapper<TypeTemplate>();

		// 查询的条件 根据品牌的名字模糊查询 like
		if (typeTemplate != null && typeTemplate.getName() != null) {
			entityWrapper.like("name", typeTemplate.getName());
		}
		entityWrapper.eq("del", 0);// 查询未删除 如果删除了就不查询出来 0表示未删除 1表示删除
									// (数据库并非是真删除而是作逻辑删除)
		entityWrapper.orderBy("id").last(" desc");

		results = typeTemplateService.selectPage(page, entityWrapper);
		// 获取总数
		int totalCount = ((Long) results.getTotal()).intValue();
		// 查询是否有上一页
		boolean hasPrevious = results.hasPrevious();
		// 查询是否有下一页
		boolean hasNext = results.hasNext();
		// 查询到每页数据
		List<TypeTemplate> typeTemplates = results.getRecords();
		for (TypeTemplate t : typeTemplates) {
			String brandIds = convertString(t.getBrandIds());
			t.setBrandIds(brandIds);

			String specIds = convertString(t.getSpecIds());
			t.setSpecIds(specIds);

			String customAttributeItems = convertString(t.getCustomAttributeItems());
			t.setCustomAttributeItems(customAttributeItems);
		}

		// 查询出所有班级
		PageResult<TypeTemplate> pageResult = new PageResult<TypeTemplate>(totalCount, pageIndex, pageSize,
				typeTemplates, typeTemplate);
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("hasPrevious", hasPrevious);
		model.addAttribute("hasNext", hasNext);
		// System.err.println(pageResult);
		return "type_template"; // 跳转模板上
	}

	/**
	 * 格式化字符串工具类
	 * 
	 * @param s
	 * @return
	 */
	public String convertString(String s) {
		// 转化为对象
		List<JsonObject> list = JSON.parseArray(s, JsonObject.class);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			JsonObject object = list.get(i);
			if (i != list.size() - 1) {
				sb.append(object.getText()).append(",");
			} else {
				sb.append(object.getText());
			}
		}
		String str = sb.toString();
		return str;
	}

	/**
	 * 02-查询所有的品牌和规格
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBrandsAndSpecifications")
	public HashMap<String, Object> getBrandsAndSpecifications() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Brand> brands = brandService.selectList(new EntityWrapper<Brand>().eq("del", 0));
		List<Specification> specifications = specificationService.selectList(new EntityWrapper<Specification>().eq("del", 0));
		map.put("brands", brands);
		map.put("specifications", specifications);
		return map;
	}

	/**
	 * 03-查询所有的品牌和规格
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Result add(String name, Integer[] brand_ids, Integer[] specification_ids, String[] custom_attribute_items) {
		System.err.println(name);
		System.err.println(Arrays.toString(brand_ids));// --->Json字符串
		System.err.println(Arrays.toString(specification_ids)); // --->Json字符串
		System.err.println(Arrays.toString(custom_attribute_items)); // --->Json字符串

		List<JsonObject> brand_list = new ArrayList<JsonObject>();
		for (Integer brand_id : brand_ids) {
			Brand brand = brandService.selectById(brand_id);
			Long id = brand.getId();
			String brandName = brand.getName();
			JsonObject jsonObject = new JsonObject(id.intValue(), brandName);
			brand_list.add(jsonObject);
		}
		String brand_json = JSON.toJSONString(brand_list);

		List<JsonObject> specification__list = new ArrayList<JsonObject>();
		for (Integer specification_id : specification_ids) {
			Specification specification = specificationService.selectById(specification_id);
			Long id = specification.getId();
			String specName = specification.getSpecName();
			JsonObject jsonObject = new JsonObject(id.intValue(), specName);
			specification__list.add(jsonObject);
		}
		String specification__json = JSON.toJSONString(specification__list);

		List<JsonObject> custom_attribute_item__list = new ArrayList<JsonObject>();
		for (int i = 0; i < custom_attribute_items.length; i++) {
			String custom_attribute_item = custom_attribute_items[i];
			JsonObject jsonObject = new JsonObject(custom_attribute_item);
			custom_attribute_item__list.add(jsonObject);
		}
		String scustom_attribute_item_json = JSON.toJSONString(custom_attribute_item__list);

		TypeTemplate template = new TypeTemplate();
		template.setName(name);
		template.setDel(0);
		template.setSpecIds(specification__json);
		template.setBrandIds(brand_json);
		template.setCustomAttributeItems(scustom_attribute_item_json);

		boolean b = typeTemplateService.insert(template);
		Result result = null;
		if (b == true) {
			result = new Result(true, "新增成功");
		} else {
			result = new Result(false, "新增失败");
		}
		return result;
	}

	/**
	 * 04-根据ID查询 为了数据更新数据回显
	 * @return
	 */
	@RequestMapping("/getById/{id}")
	@ResponseBody
	public HashMap<String,Object> getById(@PathVariable("id") Integer id) {
		TypeTemplate template = typeTemplateService.selectById(id);
		
		String brandIds = template.getBrandIds(); //json格式--->object
		List<JsonObject> objects = convertJson(brandIds);
		
		//判断是否打钩
		List<Brand> brands = brandService.selectList(new EntityWrapper<Brand>().eq("del", 0)); //所有品牌模板
		for (Brand brand : brands) {
			for (JsonObject o : objects) {
				if(o.getId().intValue()==brand.getId()){
					brand.setFlag(true);//表示勾选
				}
			}
		}
		
		String specIds = template.getSpecIds();
		List<JsonObject> objects2 = convertJson(specIds);
		
		List<Specification> specifications = specificationService.selectList(new EntityWrapper<Specification>().eq("del", 0));//所有规格
		for (Specification specification : specifications) {
			for (JsonObject o : objects2) {
				if(o.getId().intValue()==specification.getId()){
					specification.setFlag(true);//表示勾选
				}
			}
		}
		
		String customAttributeItems = template.getCustomAttributeItems();
		List<JsonObject> customAttributeItems_object = convertJson(customAttributeItems);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("template", template);
		map.put("brands", brands);
		map.put("specifications", specifications);
		map.put("customAttributeItems_object", customAttributeItems_object);
		return map;
	}
	
	
	/**
	 * 05-查询所有的品牌和规格
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Result update(Long update_id,String name, Integer[] brand_ids, Integer[] specification_ids, String[] custom_attribute_items) {
		
		System.err.println(name);
		System.err.println(Arrays.toString(brand_ids));// --->Json字符串
		System.err.println(Arrays.toString(specification_ids)); // --->Json字符串
		System.err.println(Arrays.toString(custom_attribute_items)); // --->Json字符串

		List<JsonObject> brand_list = new ArrayList<JsonObject>();
		for (Integer brand_id : brand_ids) {
			Brand brand = brandService.selectById(brand_id);
			Long id = brand.getId();
			String brandName = brand.getName();
			JsonObject jsonObject = new JsonObject(id.intValue(), brandName);
			brand_list.add(jsonObject);
		}
		String brand_json = JSON.toJSONString(brand_list);

		List<JsonObject> specification__list = new ArrayList<JsonObject>();
		for (Integer specification_id : specification_ids) {
			Specification specification = specificationService.selectById(specification_id);
			Long id = specification.getId();
			String specName = specification.getSpecName();
			JsonObject jsonObject = new JsonObject(id.intValue(), specName);
			specification__list.add(jsonObject);
		}
		String specification__json = JSON.toJSONString(specification__list);

		List<JsonObject> custom_attribute_item__list = new ArrayList<JsonObject>();
		for (int i = 0; i < custom_attribute_items.length; i++) {
			String custom_attribute_item = custom_attribute_items[i];
			JsonObject jsonObject = new JsonObject(custom_attribute_item);
			custom_attribute_item__list.add(jsonObject);
		}
		String scustom_attribute_item_json = JSON.toJSONString(custom_attribute_item__list);

		TypeTemplate template = new TypeTemplate();
		template.setId(update_id);
		template.setName(name);
		template.setDel(0);
		template.setSpecIds(specification__json);
		template.setBrandIds(brand_json);
		template.setCustomAttributeItems(scustom_attribute_item_json);

		boolean b = typeTemplateService.updateById(template);
		Result result = null;
		if (b == true) {
			result = new Result(true, "更新成功");
		} else {
			result = new Result(false, "更新失败");
		}
		return result;
	}

	
	
	public List<JsonObject> convertJson(String json){
		return JSON.parseArray(json,JsonObject.class);
	}

}
