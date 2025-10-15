package com.bookNova.app.service;

import com.bookNova.app.dao.ICategoryDao;
import com.bookNova.app.domain.Category;
import com.bookNova.app.errors.DataAccessException;
import com.bookNova.app.errors.ServiceException;

import java.util.List;

public class CategoryService {

    private final ICategoryDao categoryDao;

    public CategoryService(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    // Listar todas las categorías
    public List<Category> listCategories() throws ServiceException, DataAccessException {
        try {
            return categoryDao.listAll();
        } catch (Exception e) {
            throw new ServiceException("Error interno al listar categorías", e);
        }
    }

    // Podrías agregar más métodos aquí si quieres (crear, actualizar, borrar, etc.)

}

