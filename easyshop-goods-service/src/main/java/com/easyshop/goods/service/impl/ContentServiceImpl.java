package com.easyshop.goods.service.impl;

import com.easyshop.pojo.Content;
import com.easyshop.mapper.ContentMapper;
import com.easyshop.goods.service.ContentService;
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
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

}
