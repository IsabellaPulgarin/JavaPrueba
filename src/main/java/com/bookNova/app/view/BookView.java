package com.bookNova.app.view;

import com.bookNova.app.controller.BookController;
import com.bookNova.app.domain.Book;
import com.bookNova.app.domain.Category;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookView {

    private final BookController bookController;

    public BookView(BookController bookController) {
        this.bookController = bookController;
    }

    public void showMenu() {
        String[] options = {"Listar libros", "Crear libro", "Actualizar stock", "Salir"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "Gestión de libros",
                    "Book Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> listBooks();
                case 1 -> createBook();
                case 2 -> updateStock();
            }

        } while (choice != 3 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void listBooks() {
        Map<String, Object> response = bookController.listBooks();

        if ((int) response.get("status") == 200) {
            List<Book> books = (List<Book>) response.get("data");
            StringBuilder sb = new StringBuilder("Libros:\n");
            for (Book b : books) sb.append(b).append("\n");
            JOptionPane.showMessageDialog(null, sb.toString());
        } else {
            JOptionPane.showMessageDialog(null, response.get("error"));
        }
    }


    private void createBook() {
        try {
            // Título
            String title = JOptionPane.showInputDialog("Título:");
            if (title == null || title.isBlank()) {
                JOptionPane.showMessageDialog(null, "El título es obligatorio.");
                return;
            }

            // Autor (opcional)
            String author = JOptionPane.showInputDialog("Autor:");
            if (author == null) return;  // Cancelado

            // ISBN
            String isbn = JOptionPane.showInputDialog("ISBN:");
            if (isbn == null || isbn.isBlank()) {
                JOptionPane.showMessageDialog(null, "El ISBN es obligatorio.");
                return;
            }

            // Stock
            String stockStr = JOptionPane.showInputDialog("Cantidad en stock:");
            if (stockStr == null || stockStr.isBlank()) {
                JOptionPane.showMessageDialog(null, "El stock es obligatorio.");
                return;
            }
            int stock;
            try {
                stock = Integer.parseInt(stockStr);
                if (stock < 1) {
                    JOptionPane.showMessageDialog(null, "El stock debe ser un número positivo.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El stock debe ser un número válido.");
                return;
            }

            // Precio referencia
            String priceStr = JOptionPane.showInputDialog("Precio de referencia (decimal):");
            if (priceStr == null || priceStr.isBlank()) {
                JOptionPane.showMessageDialog(null, "El precio de referencia es obligatorio.");
                return;
            }
            float referencePrice;
            try {
                referencePrice = Float.parseFloat(priceStr);
                if (referencePrice < 0) {
                    JOptionPane.showMessageDialog(null, "El precio no puede ser negativo.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El precio debe ser un número decimal válido.");
                return;
            }

            // Categoría
            Map<String, Object> responseCategory = bookController.listCategories();

            List<Category> categories = new ArrayList<>();

            if ((int) responseCategory.get("status") == 200) {
                categories = (List<Category>) responseCategory.get("data");
            } else {
                JOptionPane.showMessageDialog(null, "Error: " + responseCategory.get("error"));
                return; // Salimos si hubo error
            }

            if (categories.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay categorías disponibles.");
                return;
            }

            String[] categoryNames = categories.stream()
                    .map(Category::getName)
                    .toArray(String[]::new);
            String selectedCategoryName = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una categoría:",
                    "Categorías",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    categoryNames,
                    categoryNames[0]);

            if (selectedCategoryName == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una categoría.");
                return;
            }

            Category selectedCategory = categories.stream()
                    .filter(cat -> cat.getName().equals(selectedCategoryName))
                    .findFirst()
                    .orElse(null);

            if (selectedCategory == null) {
                JOptionPane.showMessageDialog(null, "Categoría no válida.");
                return;
            }

            // Preparar datos para enviar
            Map<String, String> data = new HashMap<>();
            data.put("title", title.trim());
            data.put("author", author.trim());
            data.put("isbn", isbn.trim());
            data.put("stock", String.valueOf(stock));
            data.put("reference_price", String.valueOf(referencePrice));
            data.put("category_id", String.valueOf(selectedCategory.getId()));

            // Enviar al controlador
            Map<String, Object> response = bookController.createBook(data);

            if ((int) response.get("status") == 200) {
                JOptionPane.showMessageDialog(null, "Libro creado:\n" + response.get("data"));
            } else {
                JOptionPane.showMessageDialog(null, "Error: " + response.get("error"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void updateStock() {
        String idStr = JOptionPane.showInputDialog("ID del libro:");
        String stockStr = JOptionPane.showInputDialog("Nuevo stock:");

        try {
            int id = Integer.parseInt(idStr);
            int stock = Integer.parseInt(stockStr);
            Map<String, Object> response = bookController.updateStock(id, stock);

            if ((int) response.get("status") == 200) {
                JOptionPane.showMessageDialog(null, "Stock actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error: " + response.get("error"));
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos");
        }
    }
}