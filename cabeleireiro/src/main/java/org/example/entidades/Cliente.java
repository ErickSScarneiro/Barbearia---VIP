package org.example.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String cpf;

    private String telefone;

    @Column(nullable = true)
    private String corte;

    public Cliente(String nome, String cpf, String telefone, String corte) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.corte = corte;

    }
}
