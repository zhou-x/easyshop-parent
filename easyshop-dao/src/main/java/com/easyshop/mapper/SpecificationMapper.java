package com.easyshop.mapper;

import com.easyshop.pojo.Specification;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陶毅
 */
public interface SpecificationMapper extends BaseMapper<Specification> {
	public int deleteSome(Integer[] ids);

}
