package org.example.interfaceApp.cliente;

import jakarta.persistence.EntityManager;
import org.example.configuracaoJPA.ConfigJPA;
import org.example.entidades.Cliente;

public class SalvarCliente {

    private final ConfigJPA configJPA = new ConfigJPA();

    public void salvarCliente(String nome, String cpf, String telefone, String corte) {
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
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
