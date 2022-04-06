package br.romulofranco.votacao.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.romulofranco.votacao.api.model.request.PautaRequest;
import br.romulofranco.votacao.api.model.response.PautaResponse;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.pauta.Pauta;
import br.romulofranco.votacao.pauta.PautaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pauta")
@RequiredArgsConstructor
public class PautaController {

	private static final Logger logger = LoggerFactory.getLogger(PautaController.class);

	private final PautaService pautaService;
	private final ObjectMapper objectMapper;

	@GetMapping("/listar")
	public ResponseEntity<List<PautaResponse>> listarPautas(
			@RequestParam(value = "idPauta", required = false) Integer idPauta) {
		logger.info("Mostrando pautas com seu respectivo resultado");
		List<PautaResponse> response = pautaService.preparaResultado();
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<PautaResponse> criar(@RequestBody @Valid PautaRequest pautaRequest) {
		logger.info("Criando pauta...");
		Pauta pauta = objectMapper.convertValue(pautaRequest, Pauta.class);
		pauta = pautaService.criar(pauta);
		if (pauta.getStatus().equals(BusinessMessage.PAUTA_CRIADA)) {
			logger.info("Pauta criada com sucesso!");
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(objectMapper.convertValue(pauta, PautaResponse.class));
		} else {
			logger.info("Pauta não criada!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(objectMapper.convertValue(pauta, PautaResponse.class));
		}

	}

}
