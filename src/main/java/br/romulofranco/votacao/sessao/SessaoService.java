package br.romulofranco.votacao.sessao;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.romulofranco.votacao.exception.BusinessException;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.pauta.Pauta;
import br.romulofranco.votacao.pauta.PautaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessaoService {

	private static final Logger logger = LoggerFactory.getLogger(SessaoService.class);
	private final PautaRepository pautaRepository;
	private final SessaoRepository sessaoRepository;
	@Value("${votacao.tempo.sessao.minutos}")
	private Integer tempoSessaoPadrao;

	public Optional<Pauta> getPauta(Integer id) throws BusinessException {
		return pautaRepository.findById(id);
	}

	private Sessao criarSessao(Pauta pauta, LocalDateTime dataFechamento) throws BusinessException {
		Sessao sessao = Sessao.builder().dataAbertura(LocalDateTime.now())
				.dataEncerramento(dataEncerramento(dataFechamento)).pauta(pauta).build();
		sessaoRepository.save(sessao);
		sessao.setStatus(BusinessMessage.SESSAO_INICIADA);
		return sessao;
	}

	@Transactional
	public Sessao iniciarSessao(Integer idPauta, LocalDateTime dataEncerramento) {
		try {
			Pauta pauta;
			Optional<Pauta> pautas = getPauta(idPauta);
			if (Objects.requireNonNull(pautas.isPresent())) {
				pauta = pautas.get();
			} else {
				return getSessao(BusinessMessage.PAUTA_NAO_ENCONTRADA);
			}

			Optional<Sessao> sessaos = getSessao(pauta);
			if (Objects.requireNonNull(sessaos.isPresent())) {
				Sessao validSessao = sessaos.get();

				if (validSessao.getStatus().equals(BusinessMessage.SESSAO_FECHADA)) {
					sessaoRepository.delete(validSessao);
				} else
					return getSessao(BusinessMessage.SESSAO_JA_EXISTE);
			}

			return criarSessao(pauta, dataEncerramento);
		} catch (BusinessException be) {
			logger.error(be.getMessage());
			throw new BusinessException(BusinessMessage.FALHA_INICIAR_SESSAO, HttpStatus.BAD_REQUEST);
		}
	}

	private Sessao getSessao(BusinessMessage message) throws BusinessException {
		Sessao sessao = new Sessao();
		sessao.setStatus(message);
		return sessao;
	}

	private LocalDateTime dataEncerramento(LocalDateTime dataFechamento) throws BusinessException {
		return dataFechamento == null ? LocalDateTime.now().plusSeconds(tempoSessaoPadrao) : dataFechamento;
	}

	private Optional<Sessao> getSessao(Pauta pauta) throws BusinessException {
		return sessaoRepository.findByPauta(pauta);
	}

	public Integer getTempoSessaoPadrao() throws BusinessException {
		return tempoSessaoPadrao;
	}

}
