package barbearia.api.barbearia.api.entidadesbarbearia;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome; // novo campo
    private String cpf;
    private String telefone;
    private String corte; // nome do corte escolhido
    private LocalDate data; // data
    private String horario; // hora selecionada (ex: "11:30")

    private Boolean feito;

    private Boolean salvarCadastro;
    private Boolean confirmarEmail;
}
