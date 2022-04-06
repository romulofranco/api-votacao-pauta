package br.romulofranco.votacao.pauta;

import java.util.*;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.romulofranco.votacao.api.model.response.PautaResponse;
import br.romulofranco.votacao.exception.BusinessException;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.sessao.Sessao;
import br.romulofranco.votacao.sessao.SessaoRepository;
import br.romulofranco.votacao.voto.MensagemVoto;
import br.romulofranco.votacao.voto.Voto;
import br.romulofranco.votacao.voto.VotoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {

	private static final Logger logger = LoggerFactory.getLogger(PautaService.class);

	private final PautaRepository pautaRepository;
	private final SessaoRepository sessaoRepository;
	private final ObjectMapper objectMapper;
	private final VotoService votoService;

	@Transactional
	public Pauta criar(Pauta pauta) {
		try {
			Optional<Pauta> pautas = pautaRepository.findByNome(pauta.getNome());
			if (pautas.isPresent()) {
				pauta = pautas.get();
				pauta.setStatus(BusinessMessage.PAUTA_JA_EXISTE);
				return pauta;
			}
			pauta.setStatus(BusinessMessage.PAUTA_CRIADA);
			pautaRepository.save(pauta);
			return pauta;
		} catch (BusinessException be) {
			logger.error(be.getMessage());
			throw new BusinessException(BusinessMessage.FALHA_CRIAR_PAUTA, HttpStatus.BAD_REQUEST);
		}
	}

	public List<Pauta> getPautas() throws BusinessException {
		return pautaRepository.findAll();
	}

	public Optional<Pauta> getPauta(Integer id) throws BusinessException {
		return pautaRepository.findById(id);
	}

	private Optional<Sessao> getSessao(Pauta pauta) throws BusinessException {
		sessaoRepository.flush();
		return sessaoRepository.findByPauta(pauta);
	}

	public Map<String, Long> mostrarResultado(Pauta pauta) throws BusinessException {
		Sessao sessao = getSessao(pauta).get();
		Collection<Voto> votos = sessao.getVotos();

		for (Voto v : votos) {
			logger.info("Voto {}", v);
		}

		Map<String, Long> result = new HashMap<>();

		result.put("SIM: ", votos.stream().filter(v -> v.getMensagemVoto().equals(MensagemVoto.SIM)).count());
		result.put("NAO: ", votos.stream().filter(v -> v.getMensagemVoto().equals(MensagemVoto.NAO)).count());

		return result;
	}


	public List<PautaResponse> preparaResultado() {
		List<Pauta> pautas = pautaRepository.findAll();
		List<PautaResponse> result = new ArrayList<>();

		for (Pauta p : pautas) {
			PautaResponse pautaResponse = objectMapper.convertValue(p, PautaResponse.class);
			pautaResponse.setVotos(this.mostrarResultado(p));
			result.add(pautaResponse);
		}

		return result;
	}

	public PautaResponse preparaResultado(Integer idPauta) throws BusinessException {
		Pauta pauta = pautaRepository.findById(idPauta).get();
		PautaResponse result = new PautaResponse();
		PautaResponse pautaResponse = objectMapper.convertValue(pauta, PautaResponse.class);
		pautaResponse.setVotos(this.mostrarResultado(pauta));
		return result;
	}
}
