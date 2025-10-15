package com.bookNova.app;

import com.bookNova.app.controller.BookController;
import com.bookNova.app.controller.LoanController;
import com.bookNova.app.controller.UserController;
import com.bookNova.app.dao.jdbc.BookDaoJdbc;
import com.bookNova.app.dao.jdbc.CategoryDaoJdbc;
import com.bookNova.app.dao.jdbc.LoanDaoJdbc;
import com.bookNova.app.dao.jdbc.UserDaoJdbc;
import com.bookNova.app.errors.DataAccessException;
import com.bookNova.app.service.BookService;
import com.bookNova.app.service.CategoryService;
import com.bookNova.app.service.LoanService;
import com.bookNova.app.service.UserService;
import com.bookNova.app.view.*;

public class Main {

    public static void main(String[] args) throws DataAccessException {

        // Inicialización de dependencias

        // DAO
        UserDaoJdbc userDao = new UserDaoJdbc();
        BookDaoJdbc bookDao = new BookDaoJdbc();
        CategoryDaoJdbc categoryDao = new CategoryDaoJdbc();
        LoanDaoJdbc loanDao = new LoanDaoJdbc();

        // Service
        UserService userService = new UserService(userDao);
        BookService bookService = new BookService(bookDao);
        CategoryService categoryService = new CategoryService(categoryDao);
        LoanService loanService = new LoanService(loanDao, bookDao);

        // Controller
        UserController userController = new UserController(userService);
        BookController bookController = new BookController(bookService,categoryService);
        LoanController loanController = new LoanController(loanService);

        // Inicialización de Views

        // View
        UserView userView = new UserView(userController);
        BookView bookView = new BookView(bookController);
        LoanView loanView = new LoanView(loanController);

        // Admin View
        AdminView adminView = new AdminView(loanView, bookView, userView);

        // Assistant View
        AssistantView assistantView = new AssistantView(loanView);

        // Main View
        MainView mainView = new MainView(userController, assistantView, adminView, bookView);

        // Iniciar aplicación
        mainView.start();
    }
}
