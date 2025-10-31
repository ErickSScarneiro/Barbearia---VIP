package barbearia.api.barbearia.api.servicesbarbearia;

import barbearia.api.barbearia.api.entidadesbarbearia.Cliente;
import barbearia.api.barbearia.api.repositoriosbarbearia.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Adicionar cliente
    public Cliente adicionarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Buscar cliente por CPF
    public Cliente buscarCliente(String cpf) {
        return clienteRepository.findByCpf(cpf).orElse(null);
    }

    // Listar todos os clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}
