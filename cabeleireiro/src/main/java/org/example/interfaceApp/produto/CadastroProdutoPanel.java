package org.example.interfaceApp.produto;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CadastroProdutoPanel extends JPanel {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel containerPanel = new JPanel(cardLayout);

    // --- Componentes do Formulário de Adição ---
    private final JTextField addNomeField = new JTextField(20);
    private final JTextField addQtdField = new JTextField(20);
    private final JTextField addPrecoField = new JTextField(20);
    private final JComboBox<String> addCategoriaCombo = new JComboBox<>(new String[]{"Pomada", "Gel", "Shampoo", "Óleo", "Cera"});
    private final JComboBox<String> addFornecedorCombo = new JComboBox<>(new String[]{"BarberShop Co.", "HairStyle Inc.", "Premium Supplies"});

    // --- Componentes do Formulário de Edição ---
    private final JTextField editSearchField = new JTextField(20);
    private final JTextField editNomeField = new JTextField(20);
    private final JTextField editQtdField = new JTextField(20);
    private final JTextField editPrecoField = new JTextField(20);
    private final JComboBox<String> editCategoriaCombo = new JComboBox<>(new String[]{"Pomada", "Gel", "Shampoo", "Óleo", "Cera"});
    private final JComboBox<String> editFornecedorCombo = new JComboBox<>(new String[]{"BarberShop Co.", "HairStyle Inc.", "Premium Supplies"});
    private final JButton saveChangesButton = createStyledButton("SALVAR ALTERAÇÕES", new Color(215, 180, 70), Color.BLACK);

    // Dados simulados para busca e edição
    private final Map<String, Object[]> mockProductDatabase = new HashMap<>();

    public CadastroProdutoPanel() {
        // Preenche o "banco de dados" simulado
        mockProductDatabase.put("pomada", new Object[]{"Pomada Modeladora", 100, 35.50, "Pomada", "BarberShop Co."});
        mockProductDatabase.put("gel", new Object[]{"Gel Fixador", 80, 22.00, "Gel", "HairStyle Inc."});
        mockProductDatabase.put("shampoo", new Object[]{"Shampoo Anti-Queda", 50, 45.00, "Shampoo", "Premium Supplies"});

        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 55));

        // Cria e adiciona os painéis ao CardLayout
        containerPanel.add(createProductMenuPanel(), "menu");
        containerPanel.add(createAddProductFormPanel(), "add");
        containerPanel.add(createViewProductsPanel(), "view");
        containerPanel.add(createEditProductPanel(), "edit");

        add(containerPanel, BorderLayout.CENTER);
        cardLayout.show(containerPanel, "menu");
    }

    private JPanel createProductMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("GERENCIAMENTO DE PRODUTOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 3; gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(10, 10, 10, 10);

        JButton addButton = createMenuButton("Adicionar Novo Produto");
        addButton.addActionListener(e -> cardLayout.show(containerPanel, "add"));
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(addButton, gbc);

        JButton viewButton = createMenuButton("Verificar Produtos Cadastrados");
        viewButton.addActionListener(e -> cardLayout.show(containerPanel, "view"));
        gbc.gridy = 1; gbc.gridx = 1;
        panel.add(viewButton, gbc);

        JButton editButton = createMenuButton("Editar Produto");
        editButton.addActionListener(e -> cardLayout.show(containerPanel, "edit"));
        gbc.gridy = 1; gbc.gridx = 2;
        panel.add(editButton, gbc);

        return panel;
    }

    private JPanel createAddProductFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 15, 30, 15);
        panel.add(createTitleLabel("CADASTRO DE NOVO PRODUTO"), gbc);

        // Reset GBC para os campos
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST; gbc.insets = new Insets(10, 15, 10, 15);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(createFormLabel("Nome:"), gbc);
        gbc.gridx = 1; addNomeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: Pomada Modeladora"); panel.add(addNomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(createFormLabel("Quantidade:"), gbc);
        gbc.gridx = 1; addQtdField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: 100"); panel.add(addQtdField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(createFormLabel("Preço (R$):"), gbc);
        gbc.gridx = 1; addPrecoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: 35.50"); panel.add(addPrecoField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(createFormLabel("Categoria:"), gbc);
        gbc.gridx = 1; panel.add(addCategoriaCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(createFormLabel("Fornecedor:"), gbc);
        gbc.gridx = 1; panel.add(addFornecedorCombo, gbc);

        JButton saveButton = createStyledButton("SALVAR PRODUTO", new Color(215, 180, 70), Color.BLACK);
        JButton backButton = createStyledButton("VOLTAR", new Color(100, 100, 100), Color.WHITE);
        JPanel buttonPanel = createButtonPanel(saveButton, backButton);

        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso! (Simulação)", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(containerPanel, "menu");
        });
        backButton.addActionListener(e -> cardLayout.show(containerPanel, "menu"));

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.NONE; gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createViewProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(createTitleLabel("PRODUTOS EM ESTOQUE"), BorderLayout.NORTH);

        String[] colunas = {"Nome", "Qtd.", "Preço (R$)", "Categoria", "Fornecedor"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable productsTable = new JTable(tableModel);
        styleTable(productsTable);

        tableModel.setRowCount(0); // Limpa a tabela antes de adicionar
        for (Object[] data : mockProductDatabase.values()) {
            tableModel.addRow(data);
        }

        panel.add(new JScrollPane(productsTable), BorderLayout.CENTER);

        JButton backButton = createStyledButton("VOLTAR AO MENU", new Color(100, 100, 100), Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(containerPanel, "menu"));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(panel.getBackground());
        bottomPanel.add(backButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createEditProductPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(createTitleLabel("EDITAR PRODUTO"), gbc);

        // Seção de Busca
        gbc.gridy = 1;
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        searchPanel.setBackground(panel.getBackground());
        searchPanel.add(createFormLabel("Nome do Produto:"));
        editSearchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite o nome e clique em buscar");
        searchPanel.add(editSearchField);
        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener(e -> searchProduct());
        searchPanel.add(searchButton);
        panel.add(searchPanel, gbc);

        // Formulário de Edição (reset GBC)
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 2; panel.add(createFormLabel("Nome:"), gbc);
        gbc.gridx = 1; panel.add(editNomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(createFormLabel("Quantidade:"), gbc);
        gbc.gridx = 1; panel.add(editQtdField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(createFormLabel("Preço (R$):"), gbc);
        gbc.gridx = 1; panel.add(editPrecoField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(createFormLabel("Categoria:"), gbc);
        gbc.gridx = 1; panel.add(editCategoriaCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(createFormLabel("Fornecedor:"), gbc);
        gbc.gridx = 1; panel.add(editFornecedorCombo, gbc);

        // Botões
        JButton backButton = createStyledButton("VOLTAR", new Color(100, 100, 100), Color.WHITE);
        JPanel buttonPanel = createButtonPanel(saveChangesButton, backButton);
        saveChangesButton.addActionListener(e -> saveProductChanges());
        backButton.addActionListener(e -> {
            clearEditForm();
            toggleEditFields(false);
            cardLayout.show(containerPanel, "menu");
        });

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.NONE; gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(buttonPanel, gbc);

        toggleEditFields(false);
        return panel;
    }

    // --- LÓGICA DE NEGÓCIO E EVENTOS ---

    private void searchProduct() {
        String searchTerm = editSearchField.getText().toLowerCase().trim();
        if (mockProductDatabase.containsKey(searchTerm)) {
            Object[] data = mockProductDatabase.get(searchTerm);
            editNomeField.setText((String) data[0]);
            editQtdField.setText(String.valueOf(data[1]));
            editPrecoField.setText(String.valueOf(data[2]));
            editCategoriaCombo.setSelectedItem(data[3]);
            editFornecedorCombo.setSelectedItem(data[4]);
            toggleEditFields(true);
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado.", "Erro de Busca", JOptionPane.ERROR_MESSAGE);
            clearEditForm();
            toggleEditFields(false);
        }
    }

    private void saveProductChanges() {
        try {
            String key = editSearchField.getText().toLowerCase().trim();
            int quantidade = Integer.parseInt(editQtdField.getText());
            double preco = Double.parseDouble(editPrecoField.getText());

            mockProductDatabase.put(key, new Object[]{
                    editNomeField.getText(),
                    quantidade,
                    preco,
                    editCategoriaCombo.getSelectedItem(),
                    editFornecedorCombo.getSelectedItem()
            });

            JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            clearEditForm();
            toggleEditFields(false);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Quantidade e Preço devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- MÉTODOS AUXILIARES DE UI E LÓGICA ---

    private void toggleEditFields(boolean enabled) {
        editNomeField.setEnabled(enabled);
        editQtdField.setEnabled(enabled);
        editPrecoField.setEnabled(enabled);
        editCategoriaCombo.setEnabled(enabled);
        editFornecedorCombo.setEnabled(enabled);
        saveChangesButton.setEnabled(enabled);
    }

    private void clearEditForm() {
        editSearchField.setText("");
        editNomeField.setText("");
        editQtdField.setText("");
        editPrecoField.setText("");
        editCategoriaCombo.setSelectedIndex(0);
        editFornecedorCombo.setSelectedIndex(0);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
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

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(new Color(215, 180, 70));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);
        for(JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}