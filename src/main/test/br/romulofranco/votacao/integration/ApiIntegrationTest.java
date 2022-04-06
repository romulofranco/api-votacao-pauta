package br.romulofranco.votacao.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.romulofranco.votacao.VotacaoApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VotacaoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void testCriarPauta() throws Exception {
		String json = "{ \"nome\": \"string\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/pauta/").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	@Order(2)
	public void testIniciarSessao() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/sessao/1/iniciar?minutosSessao=5")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(3)
	public void testVotarPauta() throws Exception {
		String json = "{ \"cpfEleitor\": 86478403050, \"mensagemVoto\": \"SIM\"}";
		mockMvc.perform(
				MockMvcRequestBuilders.post("/v1/voto/1/votar").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(4)
	public void testListarPautas() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/pauta/listar").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}