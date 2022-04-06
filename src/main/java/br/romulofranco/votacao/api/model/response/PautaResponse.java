package br.romulofranco.votacao.api.model.response;

import java.util.List;
import java.util.Map;

import br.romulofranco.votacao.exception.BusinessMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class PautaResponse {

	@ApiModelProperty(value = "ID da Pauta")
	private String id;
	@ApiModelProperty(value = "Nome da Pauta")
	private String nome;
	@ApiModelProperty(value = "Status da criação da pauta")
	private BusinessMessage status;
	@ApiModelProperty(value = "Totalização dos votos")
	private Map<String, Long> votos;

	// public Map<String, Long> getVotos() {
	// if (this.votos.size() == 0) {
	// votos.put("SIM", Long.valueOf(0));
	// votos.put("NAO", Long.valueOf(0));
	// }
	// return votos;
	// }
}
