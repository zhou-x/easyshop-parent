package com.easyshop.goods.service.impl;

import com.easyshop.pojo.Specification;
import com.easyshop.pojo.SpecificationOption;
import com.easyshop.mapper.SpecificationMapper;
import com.easyshop.mapper.SpecificationOptionMapper;
import com.easyshop.goods.service.SpecificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陶毅
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {

	@Autowired
	SpecificationMapper specificationMapper;
	
	@Autowired
	SpecificationOptionMapper specificationOptionMapper;
	
    // 批量删除
	public int deleteSome(Integer[] ids) {
		return specificationMapper.deleteSome(ids);
	}
	
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
//	@Override
	public void insertSpecification(Specification specification, String[] optionName, Integer[] orders) {
		//有事务 要么全部成功，要么全部失败
		specification.setDel(0);
		Integer count = specificationMapper.insert(specification);  //新增成功之后会自动返回ID
		if(count>0){
			for (int i = 0; i < orders.length; i++) {
				SpecificationOption option=new SpecificationOption();
				option.setDel(0);
				option.setSpecId(specification.getId());
				option.setOptionName(optionName[i]);
				option.setOrders(orders[i]);
				specificationOptionMapper.insert(option);
			}
		}
	}

//	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public void updatetSpecification(Specification specification, String[] optionName, Integer[] orders) {
		Integer count = specificationMapper.updateById(specification);  //根据id更新
		if(count>0){
			//规格选项表全部移除，重新增新的进去
			specificationOptionMapper.delete(new EntityWrapper<SpecificationOption>().eq("spec_id", specification.getId()));
			for (int i = 0; i < orders.length; i++) {
				SpecificationOption option=new SpecificationOption();
				option.setDel(0);
				option.setSpecId(specification.getId());
				option.setOptionName(optionName[i]);
				option.setOrders(orders[i]);
				specificationOptionMapper.insert(option);
			}
		}
	}
}
