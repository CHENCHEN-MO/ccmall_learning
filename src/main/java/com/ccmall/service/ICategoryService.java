package com.ccmall.service;

import com.ccmall.common.ServerResponse;
import com.ccmall.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);


}
