package com.easyshop.goods.service.impl;

import com.easyshop.pojo.ContentCategory;
import com.easyshop.mapper.ContentCategoryMapper;
import com.easyshop.goods.service.ContentCategoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 * 内容分类 服务实现类
 * </p>
 *
 * @author 陶毅
 */
@Service
public class ContentCategoryServiceImpl extends ServiceImpl<ContentCategoryMapper, ContentCategory> implements ContentCategoryService {

}
