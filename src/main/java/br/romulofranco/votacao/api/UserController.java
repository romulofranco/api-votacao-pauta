package br.romulofranco.votacao.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.romulofranco.votacao.api.model.response.StatusCPFResponse;
import br.romulofranco.votacao.user.User;
import br.romulofranco.votacao.user.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@GetMapping("/{cpf}/")
	public ResponseEntity checaCPF(@PathVariable("cpf") String cpf) {
		logger.info("checaCPF");
		StatusCPFResponse status = userService.checaCPF(cpf);
		logger.info("Consultando CPF do Eleitor: {} - Status: {}", cpf, status.getStatus());
		if (status.getStatus().equals(User.CPF_INVALID)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
		}
		return ResponseEntity.ok(status);
	}

}
