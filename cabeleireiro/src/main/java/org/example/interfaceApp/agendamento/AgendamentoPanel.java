package org.example.interfaceApp.agendamento;

import com.formdev.flatlaf.FlatClientProperties;
import jakarta.persistence.TypedQuery;
import org.example.configuracaoJPA.ConfigJPA;
import org.example.entidades.Agendamento;

import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AgendamentoPanel extends JPanel {

    private JTextField clienteField;
    private JTextField dataAgendamentoField;
    private JTextField horarioField;
    private JComboBox<String> corteComboBox;
    private JComboBox<String> barbeiroComboBox;

    private DefaultTableModel tableModelDia;
    private DefaultTableModel tableModelSemana;
    private JTable tabelaDia;
    private JTable tabelaSemana;

    public AgendamentoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 55));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.addTab("Novo Agendamento", criarPainelAgendamento());
        tabbedPane.addTab("Agenda do Dia", criarPainelVisualizacao("Dia"));
        tabbedPane.addTab("Agenda da Semana", criarPainelVisualizacao("Semana"));

        // Atualiza a tabela automaticamente ao trocar de aba
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            String tituloAba = tabbedPane.getTitleAt(index);

            if (tituloAba.equalsIgnoreCase("Agenda do Dia")) {
                atualizarTabela("Dia");
            } else if (tituloAba.equalsIgnoreCase("Agenda da Semana")) {
                atualizarTabela("Semana");
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel criarPainelAgendamento() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("NOVO AGENDAMENTO");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 15, 30, 15);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 15, 10, 15);

        panel.add(criarLabel("Cliente:"), gbc(0, 1));
        clienteField = new JTextField(20);
        clienteField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nome do cliente");
        panel.add(clienteField, gbc(1, 1, 2));

        panel.add(criarLabel("Data:"), gbc(0, 2));
        dataAgendamentoField = new JTextField(20);
        dataAgendamentoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "dd/MM/yyyy");
        dataAgendamentoField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panel.add(dataAgendamentoField, gbc(1, 2, 2));

        panel.add(criarLabel("Horário:"), gbc(0, 3));
        horarioField = new JTextField(20);
        horarioField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "hh:mm");
        panel.add(horarioField, gbc(1, 3, 2));

        panel.add(criarLabel("Tipo de Corte:"), gbc(0, 4));
        corteComboBox = new JComboBox<>(new String[]{"Social", "Degradê", "Navalhado", "Barba", "Completo"});
        panel.add(corteComboBox, gbc(1, 4, 2));

        panel.add(criarLabel("Barbeiro:"), gbc(0, 5));
        barbeiroComboBox = new JComboBox<>(new String[]{"Qualquer um", "João", "Pedro", "Carlos"});
        panel.add(barbeiroComboBox, gbc(1, 5, 2));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(panel.getBackground());

        JButton agendarButton = createStyledButton("AGENDAR", new Color(70, 130, 180), Color.WHITE);
        agendarButton.addActionListener(e -> agendarServico());

        JButton limparButton = createStyledButton("LIMPAR", new Color(100, 100, 100), Color.WHITE);
        limparButton.addActionListener(e -> limparCampos());

        buttonPanel.add(agendarButton);
        buttonPanel.add(limparButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel criarPainelVisualizacao(String periodo) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(50, 50, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("AGENDA DO " + periodo.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(panel.getBackground());

        JButton botaoAtualizar = createStyledButton("Atualizar", new Color(70, 130, 180), Color.WHITE);
        botaoAtualizar.addActionListener(e -> atualizarTabela(periodo));

        topPanel.add(botaoAtualizar);
        panel.add(topPanel, BorderLayout.NORTH);

        // Adicionei coluna ID oculta
        String[] colunas = {"Data", "Horário", "Cliente", "Serviço", "Feito", "ID"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // somente "Feito" é editável
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? Boolean.class : String.class;
            }
        };

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Ocultar coluna ID
        tabela.getColumnModel().getColumn(5).setMinWidth(0);
        tabela.getColumnModel().getColumn(5).setMaxWidth(0);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            if (i != 4) tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        if ("Dia".equalsIgnoreCase(periodo)) {
            tableModelDia = model;
            tabelaDia = tabela;
        } else {
            tableModelSemana = model;
            tabelaSemana = tabela;
        }

        JScrollPane scrollPane = new JScrollPane(tabela);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Listener para atualizar "Feito" no banco
        tabela.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4) { // checkbox "Feito"
                Boolean feito = (Boolean) tabela.getValueAt(row, column);
                Long id = Long.parseLong(tabela.getValueAt(row, 5).toString());

                EntityManager em = ConfigJPA.getEntityManager();
                try {
                    em.getTransaction().begin();
                    Agendamento agendamento = em.find(Agendamento.class, id);
                    if (agendamento != null) {
                        agendamento.setFeito(feito);
                    }
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    em.getTransaction().rollback();
                    ex.printStackTrace();
                } finally {
                    em.close();
                }
            }
        });

        return panel;
    }

    private void atualizarTabela(String periodo) {
        EntityManager em = ConfigJPA.getEntityManager();
        try {
            String jpql = "SELECT a FROM Agendamento a";
            LocalDate hoje = LocalDate.now();
            DefaultTableModel model;
            TypedQuery<Agendamento> query;;

            if ("Dia".equalsIgnoreCase(periodo)) {
                LocalDate amanha = hoje.plusDays(1);
                jpql += " WHERE a.data >= :hoje AND a.data < :amanha";
                query = em.createQuery(jpql, Agendamento.class);
                query.setParameter("hoje", hoje);
                query.setParameter("amanha", amanha);
                model = tableModelDia;
            } else if ("Semana".equalsIgnoreCase(periodo)) {
                LocalDate fimSemana = hoje.plusDays(7);
                jpql += " WHERE a.data >= :hoje AND a.data <= :fimSemana";
                query = em.createQuery(jpql, Agendamento.class);
                query.setParameter("hoje", hoje);
                query.setParameter("fimSemana", fimSemana);
                model = tableModelSemana;
            } else {
                query = em.createQuery(jpql, Agendamento.class);
                model = tableModelDia;
            }

            List<Agendamento> lista = query.getResultList();
            model.setRowCount(0);
            for (Agendamento a : lista) {
                model.addRow(new Object[]{
                        a.getData() != null ? a.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                        a.getHorario() != null ? a.getHorario() : "",
                        a.getNome() != null ? a.getNome() : "",
                        a.getCorte() != null ? a.getCorte() : "",
                        a.getFeito() != null ? a.getFeito() : Boolean.FALSE,
                        a.getId() // ID do agendamento
                });
            }
        } finally {
            em.close();
        }
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void agendarServico() {
        String cliente = clienteField.getText().trim();
        String dataStr = dataAgendamentoField.getText().trim();
        String horario = horarioField.getText().trim();

        if (cliente.isBlank() || dataStr.isBlank() || horario.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate data;
        try {
            data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (data.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "Não é possível agendar para datas passadas.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EntityManager em = ConfigJPA.getEntityManager();
        try {
            em.getTransaction().begin();
            Agendamento agendamento = new Agendamento();
            agendamento.setNome(cliente);
            agendamento.setData(data);
            agendamento.setHorario(horario);
            agendamento.setCorte(corteComboBox.getSelectedItem().toString());
            agendamento.setFeito(false);
            em.persist(agendamento);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela("Dia");
            atualizarTabela("Semana");
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar agendamento.", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            em.close();
        }
    }

    private void limparCampos() {
        clienteField.setText("");
        dataAgendamentoField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        horarioField.setText("");
        corteComboBox.setSelectedIndex(0);
        barbeiroComboBox.setSelectedIndex(0);
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private GridBagConstraints gbc(int x, int y, int width) {
        GridBagConstraints gbc = gbc(x, y);
        gbc.gridwidth = width;
        return gbc;
    }
}
