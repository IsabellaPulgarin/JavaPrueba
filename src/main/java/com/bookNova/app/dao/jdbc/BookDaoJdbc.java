package com.bookNova.app.dao.jdbc;

import com.bookNova.app.dao.IBookDao;
import com.bookNova.app.domain.Book;
import com.bookNova.app.config.DBconfig;
import com.bookNova.app.errors.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJdbc implements IBookDao {

    @Override
    public Book create(Book book) throws DataAccessException {
        String sql = "INSERT INTO books (isbn, title, author, total_copies, available_copies, reference_price, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getStock());
            ps.setInt(5, book.getStock());
            ps.setFloat(6, book.getReference_price());
            ps.setInt(7, book.getCategory());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    book.setId(rs.getInt(1));
                }
            }

            return book;

        } catch (SQLException e) {
            throw new DataAccessException("Error al crear el libro", e);
        }
    }

    @Override
    public Book findByIsbn(String isbn) throws DataAccessException {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBook(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar el libro por ISBN", e);
        }
    }

    @Override
    public List<Book> listAll() throws DataAccessException {
        String sql = "SELECT * FROM books ORDER BY created_at DESC";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar los libros", e);
        }

        return books;
    }

    @Override
    public boolean updateStock(int idBook, int newStock) throws DataAccessException {
        String sql = "UPDATE books SET total_copies = ?, available_copies = ? WHERE id_book = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newStock);
            ps.setInt(2, newStock);
            ps.setInt(3, idBook);

            int updated = ps.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar stock del libro", e);
        }
    }

    // =================================
    // Método auxiliar
    // =================================
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id_book"));
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setStock(rs.getInt("total_copies"));
        book.setReference_price(rs.getFloat("reference_price"));
        book.setCategory(rs.getInt("category_id"));
        book.setActive(rs.getInt("available_copies") > 0);
        book.setCreatedAt(rs.getTimestamp("created_at"));
        return book;
    }
}
