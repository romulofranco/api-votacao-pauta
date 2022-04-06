package br.romulofranco.votacao.pauta;

import java.io.Serializable;

import javax.persistence.*;

import br.romulofranco.votacao.exception.BusinessMessage;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pauta")
public class Pauta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private BusinessMessage status;
}
