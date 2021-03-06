package br.romulofranco.votacao.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.romulofranco.votacao.api.model.response.StatusCPFResponse;
import br.romulofranco.votacao.exception.BusinessException;
import br.romulofranco.votacao.exception.BusinessMessage;
import br.romulofranco.votacao.util.Util;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	private final UsuarioRepository usuarioRepository;
	@Value("${votacao.eleitor.persistir.permissao.voto}")
	private boolean persistirPermissaoVoto;

	@Transactional
	public StatusCPFResponse checaCPF(String cpf) {
		try {
			if (!Util.validaCPF(cpf))
				return new StatusCPFResponse(Usuario.UNABLE_TO_VOTE);

			if (persistirPermissaoVoto) {
				Usuario eleitor = new Usuario();
				eleitor.setCpf(cpf);
				eleitor.setEnabled(checkCPFEnabledToVote());
				usuarioRepository.save(eleitor);
				return new StatusCPFResponse(eleitor.getStatus());
			} else {
				return new StatusCPFResponse(this.checkCPFEnabledToVote() ? Usuario.ABLE_TO_VOTE : Usuario.UNABLE_TO_VOTE);
			}
		} catch (BusinessException be) {
			logger.error(be.getMessage());
			throw new BusinessException(BusinessMessage.FALHA_CHECAR_CPF, HttpStatus.BAD_REQUEST);
		}

	}

	private boolean checkCPFEnabledToVote() {
		int random = Util.random(8);
		return random > 3;
	}

	public boolean checkUserEnabled(String cpf) {
		try {
			Optional<Usuario> user = usuarioRepository.findByCpf(cpf);
			if (user.isPresent()) {
				return user.get().isEnabled();
			}
			return true;
		} catch (BusinessException e) {
			throw new BusinessException(BusinessMessage.USER_UNABLE_TO_VOTE, HttpStatus.NOT_FOUND);
		}
	}
}
