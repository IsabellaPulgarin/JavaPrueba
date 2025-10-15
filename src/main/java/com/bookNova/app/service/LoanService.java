package com.bookNova.app.service;

import com.bookNova.app.config.DBconfig;
import com.bookNova.app.dao.IBookDao;
import com.bookNova.app.dao.ILoanDao;
import com.bookNova.app.domain.Loan;
import com.bookNova.app.errors.DataAccessException;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoanService {

    private final ILoanDao loanDao;
    private final IBookDao bookDao;

    public LoanService(ILoanDao loanDao, IBookDao bookDao) {
        this.loanDao = loanDao;
        this.bookDao = bookDao;
    }

    public void registerLoan(Loan loan) throws DataAccessException {
        Connection conn = null;
        try {
            conn = DBconfig.connect();
            conn.setAutoCommit(false);

            // Actualizar stock y crear préstamo
            bookDao.updateStock(loan.getBookId(), getNewStock(conn, loan.getBookId(), -1));
            loanDao.create(loan);

            conn.commit();
            log("✅ Préstamo creado correctamente: " + loan);

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // 🔄 Revertir cambios si algo falla
                    log("⚠️ Rollback ejecutado por error en registro de préstamo");
                } catch (SQLException rollbackEx) {
                    log("❌ Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
            log("❌ Error registrando préstamo: " + e.getMessage());
            e.printStackTrace(); // ← AGREGA ESTO
            throw new DataAccessException("Error en transacción de préstamo", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close(); // ✅ Cierre manual de conexión
                } catch (SQLException closeEx) {
                    log("Error al cerrar conexión: " + closeEx.getMessage());
                }
            }
        }
    }

    public void returnBook(int loanId) throws DataAccessException {
        Connection conn = null;
        try {
            conn = DBconfig.connect();
            conn.setAutoCommit(false);

            Loan loan = loanDao.findById(loanId);
            if (loan == null)
                throw new DataAccessException("Préstamo no encontrado");

            LocalDate due = loan.getDueDate().toLocalDate();
            LocalDate today = LocalDate.now();

            double price_final = 0.0;
            if (today.isAfter(due)) {
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(due, today);
                price_final = daysLate * 1500;
                loan.setStatusLoan("LATE");
            } else {
                loan.setStatusLoan("RETURNED");
            }

            loan.setPriceFinal(price_final);
            loan.setReturnDate(java.sql.Date.valueOf(today));

            loanDao.update(loan);
            bookDao.updateStock(loan.getBookId(), getNewStock(conn, loan.getBookId(), +1));

            conn.commit();
            log("✅ Devolución procesada correctamente: " + loan);

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    log("⚠️ Rollback ejecutado por error en devolución");
                } catch (SQLException rollbackEx) {
                    log("❌ Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
            log("❌ Error procesando devolución: " + e.getMessage());
            throw new DataAccessException("Error en transacción de devolución", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    log("Error al cerrar conexión: " + closeEx.getMessage());
                }
            }
        }
    }

    public List<Loan> listAll() throws DataAccessException {
        return loanDao.findAll();
    }

    public List<Loan> listByUser(int userId) throws DataAccessException {
        return loanDao.listByUser(userId);
    }

    // === Helper privado ===
    private int getNewStock(Connection conn, int idBook, int delta) throws SQLException {
        String sql = "SELECT available_copies FROM books WHERE id_book = ?";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBook);
            var rs = ps.executeQuery();
            if (rs.next()) {
                int current = rs.getInt("available_copies");
                return current + delta;
            }
            throw new SQLException("Libro no encontrado");
        }
    }

    private void log(String msg) {
        try (FileWriter fw = new FileWriter("app.log", true)) {
            fw.write(java.time.LocalDateTime.now() + " - " + msg + "\n");
        } catch (IOException e) {
            System.err.println("Error al escribir en log: " + e.getMessage());
        }
    }
}