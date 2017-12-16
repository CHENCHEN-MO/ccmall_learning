package com.ccmall.service;

import com.ccmall.common.ServerResponse;
import com.ccmall.pojo.Product;
import com.ccmall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2017/12/14.
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    ServerResponse getProductList(int pageNum,int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
