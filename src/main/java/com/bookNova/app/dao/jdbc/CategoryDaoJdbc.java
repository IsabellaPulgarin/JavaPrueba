package com.bookNova.app.dao.jdbc;

import com.bookNova.app.config.DBconfig;
import com.bookNova.app.dao.ICategoryDao;
import com.bookNova.app.domain.Category;
import com.bookNova.app.errors.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJdbc implements ICategoryDao {

    @Override
    public List<Category> listAll() throws DataAccessException {
        String sql = "SELECT * FROM categories ORDER BY name_category ASC";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id_category"));
                c.setName(rs.getString("name_category"));
                categories.add(c);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar las categorías", e);
        }

        return categories;
    }
}
