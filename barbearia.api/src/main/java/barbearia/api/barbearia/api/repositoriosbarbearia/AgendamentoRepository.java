package barbearia.api.barbearia.api.repositoriosbarbearia;

import barbearia.api.barbearia.api.entidadesbarbearia.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    // Buscar por CPF
    List<Agendamento> findByCpf(String cpf);

    // Buscar por nome do cliente
    List<Agendamento> findByNome(String nomeCliente);

    // Opcional: buscar por data
    List<Agendamento> findByData(java.time.LocalDate data);

    List<Agendamento> findByFeitoFalse();
}
