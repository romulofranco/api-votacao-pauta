package br.romulofranco.votacao.api.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserRequest {

	@ApiModelProperty(value = "CPF do eleitor", example = "38649231071")
	@NotNull(message = "CPF do eleitor obrigatório.")
	private String cpfEleitor;
}
