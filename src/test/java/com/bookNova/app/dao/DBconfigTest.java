package com.bookNova.app.dao;

import com.bookNova.app.config.DBconfig;
import com.bookNova.app.errors.DataAccessException;
import org.junit.jupiter.api.Test;
import java.sql.Connection;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class DBconfigTest {

    @Test
    public void testConexionBaseDatos() {
        try (Connection conn = DBconfig.connect()) {
            assertNotNull(conn);
            System.out.println("✅ Conexión a la base de datos establecida correctamente.");
        } catch (DataAccessException e) {
            fail("❌ Error de conexión: " + e.getMessage());
        } catch (Exception e) {
            fail("❌ Excepción inesperada: " + e.getMessage());
        }
    }
}
