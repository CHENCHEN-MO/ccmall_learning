package com.ccmall.service;

import com.ccmall.common.ServerResponse;
import com.ccmall.pojo.User;

/**
 * Created by Administrator on 2017/12/11.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetRestPassword(String username,String passwordNew,String foegetKen);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);
}
