package com.easyshop.goods.service;

import com.easyshop.pojo.Specification;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陶毅
 */
public interface SpecificationService extends IService<Specification> {
	
	public int deleteSome(Integer[] ids); //批量删除

	public void insertSpecification(Specification specification,String[] optionName,Integer[] orders);

	public void updatetSpecification(Specification specification, String[] optionName, Integer[] orders);
}
