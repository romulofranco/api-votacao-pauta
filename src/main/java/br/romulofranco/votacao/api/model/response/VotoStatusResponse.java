package br.romulofranco.votacao.api.model.response;

import br.romulofranco.votacao.voto.MensagemVoto;
import org.springframework.http.HttpStatus;

import br.romulofranco.votacao.exception.BusinessMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Setter
@Getter
public class VotoStatusResponse {

	private String cpf;
	private MensagemVoto msgVoto;

	@ApiModelProperty(value = "Status do voto do eleitor")
	private BusinessMessage statusVoto;

	@ApiModelProperty(value = "Status de sucesso ou erro do protocolo http")
	private HttpStatus httpStatus;



	public String getStatusVoto() {
		return statusVoto.name();
	}

	public String getReasonPhrase() {
		return statusVoto.getErrorBusinessMessage();
	}

	public String getHttpStatus() {
		return httpStatus.getReasonPhrase();
	}

	public Integer getHttpStatusCode() {
		return httpStatus.value();
	}

}
