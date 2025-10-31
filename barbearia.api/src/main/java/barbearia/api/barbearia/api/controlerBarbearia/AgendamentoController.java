package barbearia.api.barbearia.api.controllersbarbearia;

import barbearia.api.barbearia.api.entidadesbarbearia.Agendamento;
import barbearia.api.barbearia.api.entidadesbarbearia.Cliente;
import barbearia.api.barbearia.api.servicesbarbearia.AgendamentoService;
import barbearia.api.barbearia.api.servicesbarbearia.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        Agendamento agendamento = new Agendamento();

        List<String> todosHorarios = List.of(
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                "17:00", "17:30", "18:00", "18:30", "19:00", "19:30",
                "20:00", "20:30", "21:00"
        );

        List<Agendamento> agendamentosAtivos = agendamentoService.buscarAgendamentosAtivos();

        List<String> horariosOcupados = agendamentosAtivos.stream()
                .map(Agendamento::getHorario)
                .toList();

        List<String> horariosDisponiveis = todosHorarios.stream()
                .filter(h -> !horariosOcupados.contains(h))
                .toList();

        model.addAttribute("agendamento", agendamento);
        model.addAttribute("horarios", horariosDisponiveis);

        return "agendamento_form";
    }

    @PostMapping("/salvar")
    @ResponseBody
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento) {
        Cliente cliente = new Cliente(agendamento.getNome(), agendamento.getCpf(), agendamento.getTelefone());
        clienteService.adicionarCliente(cliente);

        agendamentoService.realizarAgendamento(agendamento);

        return "✅ Agendamento realizado com sucesso!";
    }


}
