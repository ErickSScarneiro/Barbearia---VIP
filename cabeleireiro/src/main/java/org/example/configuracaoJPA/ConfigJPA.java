package org.example.configuracaoJPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.entidades.Agendamento;
import org.example.entidades.Cliente;
import org.example.entidades.Corte;

import java.util.List;
import java.util.function.Consumer;

public class ConfigJPA {


    public static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("cabeleireiro");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void adicionarCliente(Cliente cliente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace(); // <- Mostra qualquer erro no console
        } finally {
            em.close();
        }
    }

    public Cliente buscarCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public void atualizarCliente(Integer id, Consumer<Cliente> modificador) {
        EntityManager em = ConfigJPA.getEntityManager();
        Cliente cliente = em.find(Cliente.class, id);

        if (cliente != null) {
            em.getTransaction().begin();
            modificador.accept(cliente); // Aplica a alteração aqui!
            em.getTransaction().commit();
        }

        em.close();

        //EXEMPLO DE UTILIZAÇÃO atualizarCliente(1, c -> c.setCorte("Social"));
        // ONDE c Representaria o Cliente

    }

    public List<Cliente> buscarTodosClientes() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Agendamento> buscarAgendamentos() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Agendamento> query = em.createQuery("SELECT c FROM Agendamento c", Agendamento.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // dentro de ConfigJPA
    public List<Corte> buscarTodosCortes() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Corte c", Corte.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void salvarCorte(Corte corte) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(corte);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace(); // <- Mostra qualquer erro no console
        } finally {
            em.close();
        }
    }


}
