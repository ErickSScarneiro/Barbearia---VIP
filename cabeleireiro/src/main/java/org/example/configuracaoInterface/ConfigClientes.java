package org.example.configuracaoInterface;

import org.example.configuracaoJPA.ConfigJPA;
import org.example.entidades.Cliente;

import java.util.List;

public class ConfigClientes {

    ConfigJPA config = new ConfigJPA();

    public void adicionarCliente(String nome, String cpf, String telefone, String corte) {
        if (nome != null && telefone != null && corte != null) {
            Cliente cliente = new Cliente(nome, cpf, telefone, corte);
            config.adicionarCliente(cliente);
        }
    }

    public List<Cliente> buscarTodosClientes() {
        return config.buscarTodosClientes();
    }
}
