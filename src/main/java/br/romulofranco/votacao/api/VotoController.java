package br.romulofranco.votacao.api;

import br.romulofranco.votacao.api.model.request.VotoRequest;
import br.romulofranco.votacao.api.model.response.VotoResponse;
import br.romulofranco.votacao.api.model.response.VotoStatusResponse;
import br.romulofranco.votacao.voto.Voto;
import br.romulofranco.votacao.voto.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/voto")
@RequiredArgsConstructor
public class VotoController {

  private static final Logger logger = LoggerFactory.getLogger(VotoController.class);

  private final VotoService votoService;
  private final ObjectMapper objectMapper;

  @PostMapping("/{idPauta}/votar")
  public ResponseEntity votar(
      @PathVariable("idPauta") Integer idPauta, @RequestBody @Valid VotoRequest voto) {
    VotoStatusResponse response = votoService.votar(idPauta, objectMapper.convertValue(voto, Voto.class));
    logger.info(response.toString());

    return ResponseEntity.status(response.getHttpStatusCode()).body(response);
  }

  @GetMapping("/{idPauta}/listar")
  public ResponseEntity<List<VotoResponse>> listVotos(@PathVariable("idPauta") Integer idPauta) {
    List<VotoResponse> response = votoService.listar(idPauta);
    return ResponseEntity.ok(response);
  }
}
