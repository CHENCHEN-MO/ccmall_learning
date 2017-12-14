package com.ccmall.service;

import com.ccmall.common.ServerResponse;
import com.ccmall.pojo.Product;

/**
 * Created by Administrator on 2017/12/14.
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);
}
