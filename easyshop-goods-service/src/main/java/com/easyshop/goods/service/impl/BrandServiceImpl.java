package com.easyshop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.easyshop.goods.service.BrandService;
import com.easyshop.mapper.BrandMapper;
import com.easyshop.pojo.Brand;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陶毅
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

	@Autowired
	BrandMapper brandMapper;
	
    // 批量删除
	public int deleteSome(Integer[] ids) {
		return brandMapper.deleteSome(ids);
	}

	
}
