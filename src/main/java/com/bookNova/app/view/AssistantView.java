package com.bookNova.app.view;

import com.bookNova.app.util.SessionManager;
import com.bookNova.app.domain.User;

import javax.swing.JOptionPane;

public class AssistantView {

    private final LoanView loanView;

    public AssistantView(LoanView loanView) {
        this.loanView = loanView;
    }

    public void showMenu() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "No hay usuario logueado.");
            return;
        }

        String[] options = {"Préstamos", "Salir"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido, " + currentUser.getName(),
                    "Menú Asistente",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> viewLoans();

            }

        } while (choice != 1 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void viewLoans() {
        loanView.showMenu();
    }

}
