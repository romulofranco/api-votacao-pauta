package br.romulofranco.votacao.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	public static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";
	public static final String CPF_INVALID = "CPF INVALID";

	@Id
	@CPF
	@NotBlank(message = "CPF é obrigatório")
	private String cpf;
	private boolean enabled;

	@Transient
	public String getStatus() {
		if (this.enabled)
			return ABLE_TO_VOTE;
		else
			return UNABLE_TO_VOTE;
	}
}
