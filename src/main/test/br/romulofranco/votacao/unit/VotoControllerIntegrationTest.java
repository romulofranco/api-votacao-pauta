package br.romulofranco.votacao.unit;


import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import br.romulofranco.votacao.voto.MensagemVoto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.romulofranco.votacao.api.VotoController;
import br.romulofranco.votacao.api.model.response.VotoStatusResponse;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.pauta.Pauta;
import br.romulofranco.votacao.pauta.PautaRepository;
import br.romulofranco.votacao.sessao.Sessao;
import br.romulofranco.votacao.sessao.SessaoRepository;
import br.romulofranco.votacao.voto.VotoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class VotoControllerIntegrationTest {

	private MockMvc mockMvc;

	@Mock
	private VotoService service;

	@InjectMocks
	private VotoController controller;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private PautaRepository pautaRepository;

	@Mock
	private SessaoRepository sessaoRepository;

	@Before
	public void setup() {
		preparaPautaWithSessao();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testVotarPautaSuccess() throws Exception {
		String json = "{ \"cpfEleitor\": 86478403050, \"mensagemVoto\": \"SIM\"}";
		VotoStatusResponse votoStatusResponse = new VotoStatusResponse("86478403050", MensagemVoto.SIM, BusinessMessage.VOTO_SUCESSO, HttpStatus.OK);

		when(service.votar(Mockito.anyInt(), Mockito.any())).thenReturn(votoStatusResponse);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/v1/voto/1/votar").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testVotarPautaFail() throws Exception {
		String json = "{ \"cpfEleitor\": 12313123, \"mensagemVoto\": \"SIM\"}";
		VotoStatusResponse votoStatusResponse = new VotoStatusResponse("12313123", MensagemVoto.ERRO,BusinessMessage.PAUTA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);

		when(service.votar(Mockito.anyInt(), Mockito.any())).thenReturn(votoStatusResponse);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/v1/voto/1/votar").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	private void preparaPautaWithSessao() {
		Pauta p = new Pauta();
		p.setNome("Romulo");
		p.setStatus(BusinessMessage.PAUTA_CRIADA);
		pautaRepository.save(p);
		Sessao sessao = new Sessao();
		sessao.setStatus(BusinessMessage.SESSAO_INICIADA);
		sessao.setDataAbertura(LocalDateTime.now());
		sessao.setDataEncerramento(LocalDateTime.now().plusMinutes(5));
		sessao.setPauta(p);
		sessaoRepository.save(sessao);
	}

}