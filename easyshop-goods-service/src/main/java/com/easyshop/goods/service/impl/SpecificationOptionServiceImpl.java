package com.easyshop.goods.service.impl;

import com.easyshop.pojo.SpecificationOption;
import com.easyshop.mapper.BrandMapper;
import com.easyshop.mapper.SpecificationOptionMapper;
import com.easyshop.goods.service.SpecificationOptionService;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陶毅
 */
@Service
public class SpecificationOptionServiceImpl extends ServiceImpl<SpecificationOptionMapper, SpecificationOption> implements SpecificationOptionService {


}
