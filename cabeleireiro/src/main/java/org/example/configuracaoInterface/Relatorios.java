package org.example.configuracaoInterface;

import org.example.configuracaoJPA.ConfigJPA;
import org.example.entidades.Cliente;

import java.util.List;

public class Relatorios {

    private final ConfigJPA configJPA = new ConfigJPA();

    /**
     * Busca todos os clientes do banco.
     * @return lista de clientes
     */
    public List<Cliente> buscarTodosClientes() {
        return configJPA.buscarTodosClientes();
    }

    // Futuramente, adicionar outros métodos para
    // gerar relatórios filtrados por período, exportar para PDF, etc.
}
