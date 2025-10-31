package org.example.interfaceApp.corte;

import com.formdev.flatlaf.FlatClientProperties;
import org.example.configuracaoJPA.ConfigJPA;
import org.example.entidades.Corte;

import javax.swing.*;
import java.awt.*;

public class CadastroCortePanel extends JPanel {

    private ConfigJPA configJPA = new ConfigJPA();

    public CadastroCortePanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(50, 50, 55));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("CADASTRO DE TIPOS DE CORTE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 15, 30, 15);
        add(titleLabel, gbc);

        // Reset GBC
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 15, 10, 15);

        // Campos
        JTextField nomeField = new JTextField(20);
        nomeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: Degradê Navalhado");
        add(criarLabel("Nome do Corte:"), gbc(0, 1, gbc));
        add(nomeField, gbc(1, 1, gbc));

        JTextField duracaoField = new JTextField(20);
        duracaoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: 45");
        add(criarLabel("Duração (min):"), gbc(0, 2, gbc));
        add(duracaoField, gbc(1, 2, gbc));

        JTextField precoField = new JTextField(20);
        precoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: 50.00");
        add(criarLabel("Preço (R$):"), gbc(0, 3, gbc));
        add(precoField, gbc(1, 3, gbc));


        /*JComboBox<String> dificuldadeCombo = new JComboBox<>(new String[]{"Fácil", "Médio", "Difícil"});
        add(criarLabel("Dificuldade:"), gbc(0, 4, gbc));
        add(dificuldadeCombo, gbc(1, 4, gbc));

         */

        // Botão Salvar
        JButton saveButton = new JButton("SALVAR CORTE");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(new Color(215, 180, 70));
        saveButton.setForeground(Color.BLACK);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(30, 0, 0, 0);

        saveButton.addActionListener(e -> {

            String nome = nomeField.getText().trim();
            String duracaoCorte = duracaoField.getText().trim();
            String precoTexto = precoField.getText().trim();

            if (nome.isEmpty() || duracaoCorte.isEmpty() || precoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Preencha todos os campos antes de salvar.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
                return; // sai do método sem tentar salvar
            }

            try {
                precoTexto = precoTexto.replace(",", ".");
                Double precoFormatado = Double.parseDouble(precoTexto);

                Corte corte = new Corte(nome, duracaoCorte, precoFormatado);
                configJPA.salvarCorte(corte);

                JOptionPane.showMessageDialog(
                        null,
                        "Corte salvo com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // limpa os campos após salvar
                nomeField.setText("");
                duracaoField.setText("");
                precoField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Preço inválido! Use apenas números (Ex: 50.00)",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        add(saveButton, gbc);
    }

    // Métodos auxiliares de UI
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private GridBagConstraints gbc(int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

}