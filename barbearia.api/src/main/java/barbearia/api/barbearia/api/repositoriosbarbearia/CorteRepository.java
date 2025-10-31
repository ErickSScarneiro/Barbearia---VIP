package barbearia.api.barbearia.api.repositoriosbarbearia;

import barbearia.api.barbearia.api.entidadesbarbearia.Corte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorteRepository extends JpaRepository<Corte, Integer>  {
    Optional<Corte> findByNome(String nome);

}
