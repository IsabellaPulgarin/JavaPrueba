package com.bookNova.app.controller;

import com.bookNova.app.domain.Book;
import com.bookNova.app.domain.Category;
import com.bookNova.app.errors.*;
import com.bookNova.app.service.BookService;
import com.bookNova.app.errors.*;
import com.bookNova.app.service.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    public Map<String, Object> createBook(Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();

        try {
            String title = data.get("title");
            String author = data.get("author");
            String isbn = data.get("isbn");
            int stock = Integer.parseInt(data.getOrDefault("stock", "1"));
            float price = Float.parseFloat(data.getOrDefault("reference_price", "0.0"));
            int categoryId = Integer.parseInt(data.get("category_id"));

            Book book = new Book(title, author, isbn, stock, stock > 0,price,categoryId);
            book = bookService.createBook(book);

            response.put("status", 200);
            response.put("data", book);

        } catch (BadRequestException e) {
            response.put("status", 400);
            response.put("error", e.getMessage());

        } catch (ConflictException e) {
            response.put("status", 409);
            response.put("error", e.getMessage());

        } catch (ServiceException e) {
            response.put("status", 500);
            response.put("error", "Error interno del servidor");

        } catch (DataAccessException e) {
            response.put("status", 500);
            response.put("error", "Error de acceso a datos");

        } finally {
            System.out.println("→ Operación crear libro finalizada");
        }

        return response;
    }

    public Map<String, Object> listBooks() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Book> books = bookService.listBooks();
            response.put("status", 200);
            response.put("data", books);

        } catch (ServiceException e) {
            response.put("status", 500);
            response.put("error", "Error interno del servidor");
        } catch (DataAccessException e) {
            response.put("status", 500);
            response.put("error", "Error de acceso a datos");
        } finally {
            System.out.println("→ Operación listar libros finalizada");
        }

        return response;
    }

    public Map<String, Object> updateStock(int idBook, int newStock) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean ok = bookService.updateStock(idBook, newStock);
            response.put("status", 200);
            response.put("data", ok);

        } catch (BadRequestException e) {
            response.put("status", 400);
            response.put("error", e.getMessage());

        } catch (NotFoundException e) {
            response.put("status", 404);
            response.put("error", e.getMessage());

        } catch (ServiceException | DataAccessException e) {
            response.put("status", 500);
            response.put("error", "Error interno del servidor");
        } finally {
            System.out.println("→ Operación actualizar stock finalizada");
        }

        return response;
    }

    //CAtegory

    public Map<String, Object> listCategories() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Category> categories = categoryService.listCategories();
            response.put("status", 200);
            response.put("data", categories);

        } catch (ServiceException e) {
            response.put("status", 500);
            response.put("error", "Error interno del servidor");

        } catch (DataAccessException e) {
            response.put("status", 500);
            response.put("error", "Error de acceso a datos");

        } finally {
            System.out.println("→ Operación listar categorías finalizada");
        }

        return response;
    }


}
