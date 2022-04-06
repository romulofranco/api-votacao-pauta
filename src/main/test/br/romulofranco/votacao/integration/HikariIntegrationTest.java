package br.romulofranco.votacao.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.romulofranco.votacao.VotacaoApplication;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.pauta.Pauta;
import br.romulofranco.votacao.pauta.PautaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VotacaoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.datasource.type=com.zaxxer.hikari.HikariDataSource")
@ActiveProfiles("test")
public class HikariIntegrationTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PautaRepository pautaRepository;

	@Test
	public void hikariConnectionPoolIsConfigured() {
		assertEquals("com.zaxxer.hikari.HikariDataSource", dataSource.getClass().getName());
	}

	@Test
	public void testPautaRepository() {
		Pauta p = new Pauta();
		p.setNome("Romulo");
		p.setStatus(BusinessMessage.PAUTA_CRIADA);
		p = pautaRepository.save(p);
		assertNotNull(pautaRepository.findByNome("Romulo"));
	}

}