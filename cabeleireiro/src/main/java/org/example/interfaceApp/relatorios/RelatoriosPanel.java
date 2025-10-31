package org.example.interfaceApp.relatorios;

import org.example.configuracaoInterface.Relatorios;
import org.example.entidades.Cliente;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class RelatoriosPanel extends JPanel {

    private JTable reportTable;
    private DefaultTableModel tableModel;
    private ButtonGroup periodGroup;

    public RelatoriosPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(50, 50, 55));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("PAINEL DE RELATÓRIOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(215, 180, 70));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Painel de Opções (Esquerda)
        add(createOptionsPanel(), BorderLayout.WEST);

        // Painel de Resultados (Centro)
        add(createResultsPanel(), BorderLayout.CENTER);

        // Painel de Ações (Abaixo)
        add(createActionsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(new Color(55, 55, 60));
        optionsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Filtrar por Período",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), new Color(215, 180, 70)
        ));
        optionsPanel.setPreferredSize(new Dimension(220, 0));

        periodGroup = new ButtonGroup();
        String[] periods = {"Últimos 7 dias", "Últimos 15 dias", "Este Mês", "Mês Passado", "Todo o Período"};

        for (String period : periods) {
            JRadioButton radio = new JRadioButton(period);
            radio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            radio.setForeground(Color.WHITE);
            radio.setOpaque(false);
            radio.setCursor(new Cursor(Cursor.HAND_CURSOR));

            periodGroup.add(radio);
            optionsPanel.add(radio);
            optionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            if (period.equals("Últimos 7 dias")) {
                radio.setSelected(true);
            }
        }
        return optionsPanel;
    }

    private JScrollPane createResultsPanel() {
        String[] columnNames = {"ID", "Nome", "Telefone", "Corte"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(tableModel);
        reportTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reportTable.setRowHeight(25);
        reportTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Clientes Cadastrados",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), Color.WHITE
        ));
        return scrollPane;
    }

    private JPanel createActionsPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton generateBtn = new JButton("GERAR RELATÓRIO");
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        generateBtn.setBackground(new Color(70, 130, 180));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        generateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        generateBtn.addActionListener(this::generateReport);

        JButton exportBtn = new JButton("EXPORTAR PARA PDF");
        exportBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exportBtn.setBackground(new Color(215, 180, 70));
        exportBtn.setForeground(Color.BLACK);
        exportBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        exportBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // exportBtn ainda não implementado

        buttonPanel.add(generateBtn);
        buttonPanel.add(exportBtn);
        return buttonPanel;
    }

    private void generateReport(ActionEvent e) {
        // Limpa a tabela
        tableModel.setRowCount(0);

        try {
            Relatorios relatorios = new Relatorios();
            List<Cliente> clientes = relatorios.buscarTodosClientes();

            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nenhum cliente encontrado no banco.",
                        "Informação",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Cliente c : clientes) {
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        c.getTelefone(),
                        c.getCorte()
                });
            }

            JOptionPane.showMessageDialog(this,
                    "Relatório de clientes gerado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar relatório: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
