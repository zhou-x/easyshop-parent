package com.easyshop.goods.service;

import com.easyshop.pojo.Brand;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陶毅
 */
public interface BrandService extends IService<Brand> {

	public int deleteSome(Integer[] ids); //批量删除
}
