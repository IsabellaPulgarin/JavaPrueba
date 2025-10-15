package com.bookNova.app.controller;

import com.bookNova.app.domain.Loan;
import com.bookNova.app.errors.DataAccessException;
import com.bookNova.app.service.LoanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public Map<String, Object> createLoan(Loan loan) {
        Map<String, Object> response = new HashMap<>();
        try {
            loanService.registerLoan(loan);
            response.put("status", 200);
            response.put("data", loan);
        } catch (DataAccessException e) {
            response.put("status", 500);
            response.put("error", e.getMessage());
        }
        return response;
    }

    public Map<String, Object> returnBook(int loanId) {
        Map<String, Object> response = new HashMap<>();
        try {
            loanService.returnBook(loanId);
            response.put("status", 200);
            response.put("message", "Libro devuelto correctamente.");
        } catch (DataAccessException e) {
            response.put("status", 500);
            response.put("error", e.getMessage());
        }
        return response;
    }

    public List<Loan> listAll() throws DataAccessException {
        return loanService.listAll();
    }

    public List<Loan> listByUser(int idUser) throws DataAccessException {
        return loanService.listByUser(idUser);
    }
}

