package barbearia.api.barbearia.api.servicesbarbearia;

import barbearia.api.barbearia.api.entidadesbarbearia.Corte;
import barbearia.api.barbearia.api.repositoriosbarbearia.CorteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CorteService {

    private final CorteRepository corteRepository;

    public CorteService(CorteRepository corteRepository) {
        this.corteRepository = corteRepository;
    }

    public Corte adicionarCorte(Corte corteDeCabelo) {
        return corteRepository.save(corteDeCabelo);
    }

    public Corte buscarCorte(Corte corteDeCabelo) {
        return corteRepository.findByNome(corteDeCabelo.getNome())
                .orElse(null);
    }

    public Boolean verificarCorte(Corte corteDeCabelo) {
        Optional<Corte> existente = corteRepository.findByNome(corteDeCabelo.getNome());

        return existente.isPresent();
    }

}
