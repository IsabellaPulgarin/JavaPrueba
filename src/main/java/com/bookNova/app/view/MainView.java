package com.bookNova.app.view;

import com.bookNova.app.controller.UserController;
import com.bookNova.app.domain.User;
import com.bookNova.app.util.SessionManager;

import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;

public class MainView {

    private final UserController userController;
    private final AssistantView assistantView;
    private final AdminView adminView;
    private final BookView bookView;

    public MainView(UserController userController,
                    AssistantView assistantView,
                    AdminView adminView,
                    BookView bookView) {
        this.userController = userController;
        this.assistantView = assistantView;
        this.adminView = adminView;
        this.bookView = bookView;
    }

    public void start() {
        boolean loginOk = false;

        while (!loginOk) {
            // LOGIN
            String email = JOptionPane.showInputDialog("Ingrese su email:");
            String password = JOptionPane.showInputDialog("Ingrese su contraseña:");

            // Crear el HashMap con los datos de login
            Map<String, String> loginData = new HashMap<>();
            loginData.put("email", email);
            loginData.put("password", password);

// Llamar al controlador pasando el HashMap
            Map<String, Object> response = userController.login(loginData);
            System.out.println(response.get("status"));
// Procesar la respuesta
            if ((int) response.get("status") == 200) {
                User user = (User) response.get("data");

                if (!user.isActive()) {
                    JOptionPane.showMessageDialog(null, "Usuario inactivo. Contacte con administración.");
                } else {
                    SessionManager.setCurrentUser(user);

                    loginOk = true;

                    if ("ASSISTANT".equals(user.getRole())) {
                        assistantView.showMenu();
                    } else {
                        adminView.showMenu();
                    }
                }
            } else {
                int status = (int) response.get("status");
                String error = (String) response.get("error");

                switch (status) {
                    case 400 -> JOptionPane.showMessageDialog(null, "Datos inválidos: " + error);
                    case 401 -> JOptionPane.showMessageDialog(null, "No autorizado: " + error);
                    case 403 -> JOptionPane.showMessageDialog(null, "Usuario inactivo. Contacte con administración.");
                    case 404 -> JOptionPane.showMessageDialog(null, "Usuario no encontrado: " + error);
                    case 500 -> JOptionPane.showMessageDialog(null, "Error interno del servidor");
                    default -> JOptionPane.showMessageDialog(null, "Error: " + error);
                }
            }

        }
    }
}
