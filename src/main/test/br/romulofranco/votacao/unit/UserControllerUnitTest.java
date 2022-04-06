package br.romulofranco.votacao.unit;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.romulofranco.votacao.api.UserController;
import br.romulofranco.votacao.api.model.response.StatusCPFResponse;
import br.romulofranco.votacao.user.User;
import br.romulofranco.votacao.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class UserControllerUnitTest {
	private MockMvc mockMvc;

	@Mock
	private UserService service;

	@InjectMocks
	private UserController controller;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testChecaCPFSuccess() throws Exception {
		when(service.checaCPF("86478403050")).thenReturn(new StatusCPFResponse(User.ABLE_TO_VOTE));
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/86478403050/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testChecaCPFFail() throws Exception {
		when(service.checaCPF("86478403050")).thenReturn(new StatusCPFResponse(User.UNABLE_TO_VOTE));
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/86478403050/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testChecaCPFInvalid() throws Exception {
		when(service.checaCPF("12313123")).thenReturn(new StatusCPFResponse(User.CPF_INVALID));
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/12313123/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}