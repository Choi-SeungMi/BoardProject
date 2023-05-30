package com.seungmi.openBoard.dao;

import com.seungmi.openBoard.domain.*;
import com.seungmi.openBoard.domain.User;

public interface UserDao {
    User selectUser(String id) throws Exception;
    User selectUserByEmail(String email) throws Exception;
    int deleteUser(String id) throws Exception;
    int deleteUserTmp(String id) throws Exception;
    int insertUser(User user) throws Exception;
    int insertUserTmp(User user) throws Exception;
    int updateUser(User user) throws Exception;
    int count() throws Exception;
    void deleteAll() throws Exception;
}