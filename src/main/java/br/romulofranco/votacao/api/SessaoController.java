package br.romulofranco.votacao.api;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.romulofranco.votacao.api.model.response.SessaoResponse;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.sessao.Sessao;
import br.romulofranco.votacao.sessao.SessaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/sessao")
@RequiredArgsConstructor
public class SessaoController {

	private static final Logger logger = LoggerFactory.getLogger(SessaoController.class);
	private final SessaoService sessaoService;
	private final ObjectMapper objectMapper;

	@PostMapping("/{idPauta}/iniciar")
	public ResponseEntity iniciar(@PathVariable("idPauta") Integer idPauta,
			@RequestParam(value = "minutosSessao", required = false) Integer minutosSessao) {

		logger.info("iniciar");

		ResponseEntity responseEntity;
		Sessao sessao;
		if (minutosSessao == null)
			sessao = sessaoService.iniciarSessao(idPauta,
					LocalDateTime.now().plusMinutes(sessaoService.getTempoSessaoPadrao()));
		else {
			sessao = sessaoService.iniciarSessao(idPauta, LocalDateTime.now().plusMinutes(minutosSessao));
		}

		if (sessao.getStatus().equals(BusinessMessage.SESSAO_INICIADA)) {
			logger.info("Sessão de votação iniciada, o tempo de votação encerra às {}", sessao.getDataEncerramento());
			return ResponseEntity.ok(objectMapper.convertValue(sessao, SessaoResponse.class));
		} else {
			logger.info("Sessão de votação não iniciada - {}", sessao.getStatus());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(objectMapper.convertValue(sessao, SessaoResponse.class));
		}

	}

}
