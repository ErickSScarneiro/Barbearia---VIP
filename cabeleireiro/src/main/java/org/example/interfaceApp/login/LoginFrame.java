package org.example.interfaceApp.login;

import com.formdev.flatlaf.FlatClientProperties;
import org.example.interfaceApp.agendamento.menuJFrame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Barbearia VIP - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal com fundo escuro
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(50, 50, 55));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Título
        JLabel titleLabel = new JLabel("BEM-VINDO A BARBEARIA VIP");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(titleLabel, gbc);

        // Resetar Insets
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campo Usuário
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        userField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite seu usuário");
        gbc.gridx = 1;
        mainPanel.add(userField, gbc);

        // Campo Senha
        JLabel passLabel = new JLabel("Senha:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        passField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite sua senha");
        gbc.gridx = 1;
        mainPanel.add(passField, gbc);

        // Botão de Login
        JButton loginButton = new JButton("ENTRAR");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(215, 180, 70));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 0, 0, 0);
        mainPanel.add(loginButton, gbc);

        // Ação do botão
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if ("admin".equals(username) && "admin".equals(password)) {
                SwingUtilities.invokeLater(() -> {
                    new MainMenuFrame().setVisible(true);
                    dispose();
                });
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciais inválidas. Tente novamente.",
                        "Erro de Login",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}