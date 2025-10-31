package org.example.interfaceApp.cliente;

import com.formdev.flatlaf.FlatClientProperties;
import jakarta.persistence.EntityManager;
import org.example.configuracaoJPA.ConfigJPA;
import org.example.entidades.Cliente;
import org.example.entidades.Corte;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CadastroClientePanel extends JPanel {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel containerPanel = new JPanel(cardLayout);

    private final ConfigJPA configJPA = new ConfigJPA();
    private final JTextField nomeField = new JTextField(25);
    private final JTextField cpfField = new JTextField(25);
    private final JTextField telefoneField = new JTextField(25);
    private final JComboBox<String> corteComboBox = new JComboBox<>();

    public CadastroClientePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 55));

        // Preenche o comboBox com cortes do banco
        atualizarCortes();

        JPanel menuPanel = menuCliente();
        JPanel addClientPanel = cadastroNovoCliente();
        JPanel viewClientsPanel = clientesCadastrados();

        containerPanel.add(menuPanel, "menu");
        containerPanel.add(addClientPanel, "add");
        containerPanel.add(viewClientsPanel, "view");

        add(containerPanel, BorderLayout.CENTER);

        cardLayout.show(containerPanel, "menu");
    }

    private void atualizarCortes() {
        corteComboBox.removeAllItems();
        EntityManager em = configJPA.getEntityManager();
        try {
            List<Corte> cortes = em.createQuery("SELECT c FROM Corte c", Corte.class).getResultList();
            for (Corte c : cortes) {
                corteComboBox.addItem(c.getNome());
            }
        } finally {
            em.close();
        }
        corteComboBox.addItem("Outros"); // sempre adiciona "Outros" no final
    }

    private JPanel menuCliente() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("GERENCIAMENTO DE CLIENTES");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(titleLabel, gbc);

        JButton addButton = createMenuButton("Adicionar Novo Cliente");
        addButton.addActionListener(e -> {
            atualizarCortes(); // atualiza cortes sempre que abrir o formulário
            cardLayout.show(containerPanel, "add");
        });
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(addButton, gbc);

        JButton viewButton = createMenuButton("Verificar Clientes Cadastrados");
        viewButton.addActionListener(e -> {
            containerPanel.remove(2);
            containerPanel.add(clientesCadastrados(), "view"); // adiciona painel atualizado
            cardLayout.show(containerPanel, "view");
        });
        gbc.gridx = 1;
        panel.add(viewButton, gbc);

        return panel;
    }

   private JPanel cadastroNovoCliente() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("CADASTRO DE NOVO CLIENTE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 15, 30, 15);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 15, 10, 15);

        // Campos do formulário
        panel.add(criarLabel("Nome:"), gbc(0, 1, gbc));
        nomeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: João");
        panel.add(nomeField, gbc(1, 1, gbc));

        panel.add(criarLabel("CPF:"), gbc(0, 2, gbc));
        cpfField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "xxx.xxx.xxx-xx");
        panel.add(cpfField, gbc(1, 2, gbc));

        panel.add(criarLabel("Telefone:"), gbc(0, 3, gbc));
        telefoneField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(XX) XXXXX-XXXX");
        panel.add(telefoneField, gbc(1, 3, gbc));

        panel.add(criarLabel("Corte Preferido:"), gbc(0, 4, gbc));
        panel.add(corteComboBox, gbc(1, 4, gbc));

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(panel.getBackground());

        JButton saveButton = createStyledButton("SALVAR CLIENTE", new Color(215, 180, 70), Color.BLACK);
        saveButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String corte = (String) corteComboBox.getSelectedItem();

            if (nome.isBlank() || cpf.isBlank() || telefone.isBlank()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                new SalvarCliente().salvarCliente(nome, cpf, telefone, corte); // só passa os parâmetros
                JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                nomeField.setText("");
                cpfField.setText("");
                telefoneField.setText("");
                corteComboBox.setSelectedIndex(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = createStyledButton("VOLTAR", new Color(100, 100, 100), Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(containerPanel, "menu"));

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel clientesCadastrados() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("CLIENTES CADASTRADOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Corte"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable clientsTable = new JTable(tableModel);
        clientsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clientsTable.setRowHeight(25);
        clientsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < clientsTable.getColumnCount(); i++) {
            clientsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        atualizarTabela(tableModel);

        panel.add(new JScrollPane(clientsTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setBackground(panel.getBackground());

        JButton backButton = createStyledButton("VOLTAR AO MENU", new Color(100, 100, 100), Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(containerPanel, "menu"));

        JButton refreshButton = createStyledButton("ATUALIZAR", new Color(70, 130, 180), Color.WHITE);
        refreshButton.addActionListener(e -> atualizarTabela(tableModel));

        bottomPanel.add(refreshButton);
        bottomPanel.add(backButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void atualizarTabela(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        EntityManager em = configJPA.getEntityManager();
        try {
            List<Cliente> listaClientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
            for (Cliente c : listaClientes) {
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        c.getCpf(),
                        c.getTelefone(),
                        c.getCorte()
                });
            }
        } finally {
            em.close();
        }
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        return button;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        return button;
    }

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
