package br.romulofranco.votacao.sessao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.romulofranco.votacao.pauta.Pauta;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
	Optional<Sessao> findByPauta(Pauta pauta);
}
