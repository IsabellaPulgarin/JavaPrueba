package com.bookNova.app.domain;


import java.sql.Date;
import java.sql.Timestamp;

public class Loan {
    private int id;
    private int userId;
    private int bookId;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;
    private String statusLoan; // ACTIVE, RETURNED, LATE
    private double priceFinal;
    private Timestamp createdAt;

    public Loan() {}

    public Loan(int id, int userId, int bookId, Date loanDate, Date dueDate,
                String statusLoan, double priceFinal, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.statusLoan = statusLoan;
        this.priceFinal = priceFinal;
        this.createdAt = createdAt;
    }

    public Loan(int userId, int bookId, Date loanDate, Date dueDate) {
        this(0, userId, bookId, loanDate, dueDate, "ACTIVE", 0.00, null);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public Date getLoanDate() { return loanDate; }
    public void setLoanDate(Date loanDate) { this.loanDate = loanDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public String getStatusLoan() { return statusLoan; }
    public void setStatusLoan(String statusLoan) { this.statusLoan = statusLoan; }

    public double getPriceFinal() { return priceFinal; }
    public void setPriceFinal(double priceFinal) { this.priceFinal = priceFinal; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Préstamo #" + id +
                " | Usuario: " + userId +
                " | Libro: " + bookId +
                " | Estado: " + statusLoan +
                " | Multa: $" + priceFinal +
                " | Fecha de préstamo: " + loanDate +
                (dueDate != null ? " | Fecha de devolución: " + dueDate : "");

    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}