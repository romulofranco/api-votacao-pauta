package br.romulofranco.votacao.voto;

import br.romulofranco.votacao.api.model.response.VotoResponse;
import br.romulofranco.votacao.api.model.response.VotoStatusResponse;
import br.romulofranco.votacao.exception.BusinessException;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.pauta.Pauta;
import br.romulofranco.votacao.pauta.PautaRepository;
import br.romulofranco.votacao.sessao.Sessao;
import br.romulofranco.votacao.sessao.SessaoRepository;
import br.romulofranco.votacao.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotoService {

  @Value("${votacao.eleitor.persistir.permissao.voto}")
  private boolean persistirPermissaoVoto;

  private static final Logger logger = LoggerFactory.getLogger(VotoService.class);
  private final PautaRepository pautaRepository;
  private final SessaoRepository sessaoRepository;
  private final VotoRepository votoRepository;
  private final UserService userService;

  public Optional<Pauta> getPauta(Integer id) {
    return pautaRepository.findById(id);
  }

  private Optional<Sessao> getSessaoVotacao(Pauta pauta) {
    return sessaoRepository.findByPauta(pauta);
  }

  @Transactional
  public VotoStatusResponse votar(Integer idPauta, Voto voto) {
    try {
      Optional<Pauta> pauta = getPauta(idPauta);
      if (!pauta.isPresent())
        return new VotoStatusResponse(
            voto.getCpfEleitor(),
            MensagemVoto.ERRO,
            BusinessMessage.PAUTA_NAO_ENCONTRADA,
            HttpStatus.NOT_FOUND);

      Optional<Sessao> sessao = getSessaoVotacao(pauta.get());
      if (!sessao.isPresent())
        return new VotoStatusResponse(
            voto.getCpfEleitor(),
            MensagemVoto.ERRO,
            BusinessMessage.SESSAO_NAO_ENCONTRADA,
            HttpStatus.NOT_FOUND);

      Sessao validSessao = sessao.get();

      if (LocalDateTime.now().isAfter(validSessao.getDataEncerramento())) {
        validSessao.setStatus(BusinessMessage.SESSAO_FECHADA);
        sessaoRepository.save(validSessao);
        return new VotoStatusResponse(
            voto.getCpfEleitor(),
            MensagemVoto.ERRO,
            BusinessMessage.SESSAO_FECHADA,
            HttpStatus.BAD_REQUEST);
      }

      voto.setSessao(sessao.get());
      voto.setDataHora(LocalDateTime.now());

      if (votoRepository.existsBySessaoAndCpfEleitor(validSessao, voto.getCpfEleitor())) {
        return new VotoStatusResponse(
            voto.getCpfEleitor(),
            MensagemVoto.ERRO,
            BusinessMessage.VOTO_JA_REGISTRADO,
            HttpStatus.ALREADY_REPORTED);
      }

      if (persistirPermissaoVoto) {
        if (!userService.checkUserEnabled(voto.getCpfEleitor())) {
          return new VotoStatusResponse(
              voto.getCpfEleitor(),
              MensagemVoto.ERRO,
              BusinessMessage.USER_UNABLE_TO_VOTE,
              HttpStatus.NOT_FOUND);
        }
      }

      votoRepository.saveAndFlush(voto);
//      this.listVotos();
      return new VotoStatusResponse(
          voto.getCpfEleitor(),
          voto.getMensagemVoto(),
          BusinessMessage.VOTO_SUCESSO,
          HttpStatus.OK);
    } catch (BusinessException be) {
      logger.error(be.getMessage());
      throw new BusinessException(BusinessMessage.FALHA_AO_VOTAR, HttpStatus.BAD_REQUEST);
    }
  }

  private void listVotos() {
    List<Voto> votos = votoRepository.findAll();
    for (Voto v : votos) {
      logger.info("Voto {}", v);
    }
  }

  public List<VotoResponse> listar(Integer idPauta) {
    Pauta pauta = pautaRepository.findById(idPauta).get();
    Sessao sessao = sessaoRepository.findByPauta(pauta).get();
    Collection<Voto> votos = new ArrayList<>(sessao.getVotos());
    List<VotoResponse> result = new ArrayList<>();
    for (Voto v : votos) {
      logger.info("Voto {}", v);
      result.add(new VotoResponse(v.getCpfEleitor(), v.getMensagemVoto()));
    }
    return result;
  }
}
