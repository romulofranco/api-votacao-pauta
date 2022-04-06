package br.romulofranco.votacao.api.model.request;

import javax.validation.constraints.NotNull;

import br.romulofranco.votacao.voto.MensagemVoto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class VotoRequest {

	@ApiModelProperty(value = "CPF do eleitor", example = "86478403050")
	@NotNull(message = "CPF do eleitor obrigatório.")
	private String cpfEleitor;

	@ApiModelProperty(value = "Voto do eleitor", example = "SIM")
	@NotNull(message = "Voto SIM ou NAO")
	private MensagemVoto mensagemVoto;
}
