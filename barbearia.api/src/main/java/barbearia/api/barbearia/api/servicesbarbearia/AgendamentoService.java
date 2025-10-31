package barbearia.api.barbearia.api.servicesbarbearia;

import barbearia.api.barbearia.api.entidadesbarbearia.Agendamento;
import barbearia.api.barbearia.api.repositoriosbarbearia.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public Agendamento realizarAgendamento(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> buscarPorCpfCliente(String cpf) {
        return agendamentoRepository.findByCpf(cpf);
    }

    public List<Agendamento> buscarPorNomeCliente(String nome) {
        return agendamentoRepository.findByNome(nome);
    }

    public List<Agendamento> buscarTodosAgendamentos() {
        return agendamentoRepository.findAll();
    }

    public List<Agendamento> buscarAgendamentosAtivos() {
        return agendamentoRepository.findByFeitoFalse();
    }
}
