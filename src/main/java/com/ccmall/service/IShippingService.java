package com.ccmall.service;

import com.ccmall.common.ServerResponse;
import com.ccmall.pojo.Shipping;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2017/12/16.
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId,Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
