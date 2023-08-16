package com.seungmi.openBoard.dao;

import com.seungmi.openBoard.domain.UserDto;

public interface UserDao {
    UserDto selectUser(String id) throws Exception;
    UserDto selectUserByEmail(String email) throws Exception;
    int deleteUser(String id) throws Exception;
    int insertUser(UserDto userDto) throws Exception;
    int updateUser(UserDto userDto) throws Exception;
    int count() throws Exception;
    void deleteAll() throws Exception;
}