package br.romulofranco.votacao.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class DocumentacaoController {
	@GetMapping("/")
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("swagger-ui.html");
	}
}
