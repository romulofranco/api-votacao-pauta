package br.romulofranco.votacao.api.model.response;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@Getter
@Setter
@AllArgsConstructor
public class StatusCPFResponse {
	private String status;
}
