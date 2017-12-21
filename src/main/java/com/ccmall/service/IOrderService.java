package com.ccmall.service;

import com.ccmall.common.ServerResponse;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by geely
 */
public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

}
