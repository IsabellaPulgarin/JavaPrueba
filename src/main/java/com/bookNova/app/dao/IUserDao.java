package com.bookNova.app.dao;

import com.bookNova.app.domain.User;
import com.bookNova.app.errors.DataAccessException;

import java.util.List;

public interface IUserDao {
    User create(User user) throws DataAccessException;
    User findByEmail(String email) throws DataAccessException;
    User findById(int id) throws DataAccessException;
    List<User> listAll() throws DataAccessException;
}

