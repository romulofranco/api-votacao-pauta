package br.romulofranco.votacao.voto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.romulofranco.votacao.sessao.Sessao;

@Repository
public interface VotoRepository extends JpaRepository<Voto, String> {
	Boolean existsBySessaoAndCpfEleitor(Sessao sessao, String cpfEleitor);
}
