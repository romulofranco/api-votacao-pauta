package br.romulofranco.votacao.sessao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.*;

import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.pauta.Pauta;
import br.romulofranco.votacao.voto.Voto;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "sessao")
public class Sessao implements Serializable {

	@ToString.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer id;

	@ToString.Include
	@Column(name = "data_abertura")
	private LocalDateTime dataAbertura;

	@ToString.Include
	@Column(name = "data_fechamento")
	private LocalDateTime dataEncerramento;

	@OneToOne
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sessao", cascade = CascadeType.REFRESH)
	private Collection<Voto> votos = new java.util.ArrayList<>();

	@ToString.Include
	@Enumerated
	private BusinessMessage status;

}
