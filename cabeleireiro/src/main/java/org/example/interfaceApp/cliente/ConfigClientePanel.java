package org.example.interfaceApp.cliente;

import jakarta.persistence.EntityManager;
import org.example.configuracaoJPA.ConfigJPA;
import org.example.entidades.Cliente;

import javax.swing.*;
import java.awt.*;

public class ConfigClientePanel extends JPanel  {

    ConfigJPA configJPA = new ConfigJPA();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel containerPanel = new JPanel(cardLayout);


    private void salvarCliente(JTextField nomeField, JTextField cpfField, JTextField telefoneField, JComboBox<String> corteComboBox) {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String corte = (String) corteComboBox.getSelectedItem();

        if (nome.isBlank() || cpf.isBlank() || telefone.isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntityManager em = configJPA.getEntityManager();
        try {
            em.getTransaction().begin();
            Cliente c = new Cliente();
            c.setNome(nome);
            c.setCpf(cpf);
            c.setTelefone(telefone);
            c.setCorte(corte);
            em.persist(c);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            nomeField.setText("");
            cpfField.setText("");
            telefoneField.setText("");
            corteComboBox.setSelectedIndex(0);

            cardLayout.show(containerPanel, "menu");

        } catch (Exception ex) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            em.close();
        }
    }
}
