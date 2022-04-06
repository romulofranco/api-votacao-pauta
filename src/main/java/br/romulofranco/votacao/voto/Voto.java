package br.romulofranco.votacao.voto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.br.CPF;

import br.romulofranco.votacao.sessao.Sessao;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "voto")
public class Voto implements Serializable {
	@ToString.Include
	@NotNull(message = "CPF do eleitor obrigatório.")
	@Id
	@Column(nullable = false)
	@CPF(message = "CPF é inválido")
	private String cpfEleitor;

	@ToString.Include
	@NotNull(message = "Mensagem de voto é obrigatório e precisa seguir o padrão: SIM/NAO")
	@Column(name = "mensagem_voto")
	@Enumerated(EnumType.STRING)
	private MensagemVoto mensagemVoto;

	@ToString.Include
	@Column(name = "data")
	private LocalDateTime dataHora;

	@ManyToOne
	@JoinColumn(name = "id_sessao")
	@Fetch(FetchMode.SELECT)
	private Sessao sessao;

}
