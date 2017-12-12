package com.ccmall.dao;

import com.ccmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User selectLogin(@Param(value = "username") String username,@Param(value = "password")String password);

    int checkEmail(String email);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username,@Param("question")String question,@Param("answer")String answer);

    int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);
}