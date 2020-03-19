package com.easyshop.mapper;

import com.easyshop.pojo.Brand;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陶毅
 */
public interface BrandMapper extends BaseMapper<Brand> {

	public int deleteSome(Integer[] ids);
}
