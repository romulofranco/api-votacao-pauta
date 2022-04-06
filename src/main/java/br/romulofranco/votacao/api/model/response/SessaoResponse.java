package br.romulofranco.votacao.api.model.response;

import java.time.LocalDateTime;

import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.pauta.Pauta;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class SessaoResponse {

	@ApiModelProperty(value = "ID da Sessao")
	private String id;
	@ApiModelProperty(value = "Data da abertura da sessao")
	private LocalDateTime dataAbertura;
	@ApiModelProperty(value = "Data de encerramento da sessao")
	private LocalDateTime dataEncerramento;
	@ApiModelProperty(value = "Pauta a ser votada na sessao")
	private Pauta pauta;
	@ApiModelProperty(value = "Status da criação da pauta")
	private BusinessMessage status;

}
