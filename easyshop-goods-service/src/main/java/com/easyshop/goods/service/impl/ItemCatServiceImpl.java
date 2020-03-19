package com.easyshop.goods.service.impl;

import com.easyshop.pojo.ItemCat;
import com.easyshop.mapper.ItemCatMapper;
import com.easyshop.goods.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author 陶毅
 */
@Service
public class ItemCatServiceImpl extends ServiceImpl<ItemCatMapper, ItemCat> implements ItemCatService {

}
