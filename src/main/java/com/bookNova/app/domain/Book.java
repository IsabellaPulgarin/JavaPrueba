package com.bookNova.app.domain;


import java.sql.Timestamp;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int stock;
    private int availableStock;
    private boolean isActive;
    private float reference_price;
    private int category;
    private Timestamp createdAt;

    public Book() {}

    public Book(String title, String author, String isbn, int stock, boolean isActive, float reference_price, int category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stock = stock;
        this.isActive = isActive;
        this.reference_price = reference_price;
        this.category = category;
    }

    public Book(int id, String title, String author, String isbn, int stock, boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stock = stock;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Book(String title, String author, String isbn, int stock, boolean isActive) {
        this(0, title, author, isbn, stock, isActive, null);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return id + " - " + title + " (" + author + ") " +
                "[Stock: " + stock + ", " + (isActive ? "Disponible" : "Agotado") + "]";
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public float getReference_price() {
        return reference_price;
    }

    public void setReference_price(float reference_price) {
        this.reference_price = reference_price;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}
