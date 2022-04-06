package br.romulofranco.votacao.api.model.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PautaRequest {
	@ApiModelProperty(value = "Nome da pauta a ser votada")
	@NotBlank(message = "Nome da pauta é obrigatótio")
	private String nome;
}
