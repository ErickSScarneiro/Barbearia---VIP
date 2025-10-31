package org.example.interfaceApp.agendamento.menuJFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import org.example.interfaceApp.agendamento.AgendamentoPanel;
import org.example.interfaceApp.cliente.CadastroClientePanel;
import org.example.interfaceApp.corte.CadastroCortePanel;
import org.example.interfaceApp.produto.CadastroProdutoPanel;
import org.example.interfaceApp.relatorios.RelatoriosPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final Color defaultButtonColor = new Color(40, 40, 45);
    private final Color hoverButtonColor = new Color(60, 60, 65);
    private final Color selectedButtonColor = new Color(70, 130, 180);

    public MainMenuFrame() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Erro ao carregar o tema FlatLaf: " + e.getMessage());
        }

        setTitle("Barbearia VIP - Sistema de Gerenciamento");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(50, 50, 55));

        createHeader(mainPanel);
        createSideMenu(mainPanel);
        createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void createHeader(JPanel mainPanel) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        headerPanel.setBackground(new Color(35, 35, 40));

        JLabel titleLabel = new JLabel("💈 Barbearia VIP - Sistema");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(215, 180, 70));
        headerPanel.add(titleLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createSideMenu(JPanel mainPanel) {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(40, 40, 45));
        sidePanel.setPreferredSize(new Dimension(260, 0));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        String[] menuItems = {"Agendamentos", "Clientes", "Cortes", "Produtos", "Relatórios"};

        for (String item : menuItems) {
            JButton button = createMenuButton(item, "icones/" + item.toLowerCase() + ".png");
            sidePanel.add(button);
            sidePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        sidePanel.add(Box.createVerticalGlue());
        JButton exitButton = createMenuButton("Sair", "icones/sair.png");
        sidePanel.add(exitButton);

        mainPanel.add(sidePanel, BorderLayout.WEST);
    }

    private void createContentPanel() {
        contentPanel.setBackground(new Color(50, 50, 55));

        // Painel de boas-vindas
        contentPanel.add(createWelcomePanel(), "Welcome");

        // Painel de Agendamentos
        AgendamentoPanel agendamentoPanel = new AgendamentoPanel();
        contentPanel.add(agendamentoPanel, "Agendamentos");

        // Outros painéis
        contentPanel.add(new CadastroClientePanel(), "Clientes");
        contentPanel.add(new CadastroCortePanel(), "Cortes");
        contentPanel.add(new CadastroProdutoPanel(), "Produtos");
        contentPanel.add(new RelatoriosPanel(), "Relatórios");

        cardLayout.show(contentPanel, "Welcome");
    }

    private JButton createMenuButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(defaultButtonColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Short.MAX_VALUE, 60));

        button.addActionListener(e -> {
            if ("Sair".equals(text)) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "Deseja realmente sair do sistema?",
                        "Confirmação de Saída",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            } else {
                cardLayout.show(contentPanel, text);
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverButtonColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultButtonColor);
            }
        });

        return button;
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 55));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Seja bem-vindo ao sistema da Barbearia VIP.<br/>Selecione uma opção no menu lateral para começar.</div></html>");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setForeground(Color.WHITE);

        panel.add(welcomeLabel);
        return panel;
    }
}
