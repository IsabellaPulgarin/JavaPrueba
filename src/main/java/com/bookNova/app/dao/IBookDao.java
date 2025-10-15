package com.bookNova.app.dao;

import com.bookNova.app.domain.Book;
import com.bookNova.app.errors.DataAccessException;

import java.util.List;

public interface IBookDao {
    Book create(Book book) throws DataAccessException;
    Book findByIsbn(String isbn) throws DataAccessException;
    List<Book> listAll() throws DataAccessException;
    boolean updateStock(int idBook, int newStock) throws DataAccessException;
}

