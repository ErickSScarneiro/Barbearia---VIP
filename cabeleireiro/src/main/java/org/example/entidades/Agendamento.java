package org.example.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    private String horario;

    private Boolean feito;

    public Agendamento(String nome, LocalDate data, String horario, String corte, Boolean feito) {
        this.nome = nome;
        this.data = data;
        this.horario = horario;
        this.corte = corte;
        this.feito = feito;
    }

}
