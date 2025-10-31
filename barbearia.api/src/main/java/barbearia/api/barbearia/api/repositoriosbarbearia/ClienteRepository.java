package barbearia.api.barbearia.api.repositoriosbarbearia;

import barbearia.api.barbearia.api.entidadesbarbearia.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCpf(String cpf);

}
