package com.bookNova.app.dao;

import com.bookNova.app.domain.Category;
import com.bookNova.app.errors.DataAccessException;

import java.util.List;

public interface ICategoryDao {

    List<Category> listAll() throws DataAccessException, DataAccessException;
}

