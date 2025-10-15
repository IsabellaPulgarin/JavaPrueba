package com.bookNova.app.dao.jdbc;

import com.bookNova.app.config.DBconfig;
import com.bookNova.app.dao.ILoanDao;
import com.bookNova.app.domain.Loan;
import com.bookNova.app.errors.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDaoJdbc implements ILoanDao {

    @Override
    public void create(Loan loan) throws DataAccessException {
        Loan newLoan = loan;
        String sql = "INSERT INTO loans (id_user, id_book, loan_date, due_date, status_loan) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, loan.getUserId());
            ps.setInt(2, loan.getBookId());
            ps.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            ps.setDate(4, new java.sql.Date(loan.getDueDate().getTime())); // due_date
            ps.setString(5, loan.getStatusLoan());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) newLoan.setId(rs.getInt(1));

        } catch (SQLException e) {
            throw new DataAccessException("Error al crear préstamo", e);
        }
    }

    @Override
    public Loan findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM loans WHERE id_loan = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapLoan(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar préstamo por ID", e);
        }
    }

    @Override
    public List<Loan> findAll() throws DataAccessException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                loans.add(mapLoan(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar préstamos", e);
        }
        return loans;
    }

    @Override
    public List<Loan> listByUser(int idUser) throws DataAccessException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE id_user = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                loans.add(mapLoan(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar préstamos del usuario", e);
        }
        return loans;
    }

    @Override
    public void update(Loan loan) throws DataAccessException {
        String sql = "UPDATE loans SET return_date = ?, status_loan = ?, price_final = ? WHERE id_loan = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(loan.getReturnDate().getTime()));
            ps.setString(2, loan.getStatusLoan());
            ps.setDouble(3, loan.getPriceFinal());
            ps.setInt(4, loan.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar préstamo", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM loan WHERE id_loan = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar préstamo", e);
        }
    }

    private Loan mapLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setId(rs.getInt("id_loan"));
        loan.setUserId(rs.getInt("id_user"));
        loan.setBookId(rs.getInt("id_book"));
        loan.setLoanDate(rs.getDate("loan_date"));
        loan.setDueDate(rs.getDate("due_date"));
        loan.setStatusLoan(rs.getString("status_loan"));
        loan.setPriceFinal(rs.getDouble("price_final"));
        loan.setCreatedAt(rs.getTimestamp("created_at"));
        return loan;
    }
}
