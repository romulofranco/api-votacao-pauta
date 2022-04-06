package br.romulofranco.votacao.pauta;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {
	Optional<Pauta> findByNome(String nome);
}
