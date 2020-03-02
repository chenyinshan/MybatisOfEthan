package com.ethan.dao;

import com.ethan.pojo.User;

public interface IUserDao {

    void insertOne(User user);

    void updateOne(User user);

    void deleteOne(User id);

}
