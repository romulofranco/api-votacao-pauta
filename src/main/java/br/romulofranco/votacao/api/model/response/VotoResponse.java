package br.romulofranco.votacao.api.model.response;

import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.voto.MensagemVoto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Setter
@Getter
public class VotoResponse {

	private String cpf;
	private MensagemVoto msgVoto;


}
