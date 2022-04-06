package br.romulofranco.votacao.exception;

public enum BusinessMessage {
	SESSAO_FECHADA {
		@Override
		public String getErrorBusinessMessage() {
			return "A sessão já foi encerrada!";
		}
	},
	SESSAO_NAO_ENCONTRADA {
		@Override
		public String getErrorBusinessMessage() {
			return "A sessão não foi encontrada!";
		}
	},
	SESSAO_JA_EXISTE {
		@Override
		public String getErrorBusinessMessage() {
			return "Já existe sessão cadastrada com este identificador!";
		}
	},
	PAUTA_NAO_ENCONTRADA {
		@Override
		public String getErrorBusinessMessage() {
			return "A pauta não foi encontrada!";
		}
	},
	VOTO_JA_REGISTRADO {
		@Override
		public String getErrorBusinessMessage() {
			return "Você já registrou seu voto, só é permitido um voto por pessoa!";
		}
	},
	CPF_INVALIDO {
		@Override
		public String getErrorBusinessMessage() {
			return "CPF inválido";
		}
	},
	USER_UNABLE_TO_VOTE {
		@Override
		public String getErrorBusinessMessage() {
			return "Eleitor não permitido a votar";
		}
	},
	VOTO_SUCESSO {
		@Override
		public String getErrorBusinessMessage() {
			return "Voto registrado com sucesso";
		}
	},
	PAUTA_JA_EXISTE {
		@Override
		public String getErrorBusinessMessage() {
			return "Voto registrado com sucesso";
		}
	},
	PAUTA_CRIADA {
		@Override
		public String getErrorBusinessMessage() {
			return "Pauta criada com sucesso";
		}
	},
	SESSAO_INICIADA {
		@Override
		public String getErrorBusinessMessage() {
			return "Sessão iniciada e pronta para receber votação";
		}
	},
	FALHA_INICIAR_SESSAO {
		@Override
		public String getErrorBusinessMessage() {
			return "Falha ao iniciar sessão. Verifique os logs";
		}
	},
	FALHA_CRIAR_PAUTA {
		@Override
		public String getErrorBusinessMessage() {
			return "Falha ao criar pauta. Verifique os logs";
		}
	},
	FALHA_AO_VOTAR {
		@Override
		public String getErrorBusinessMessage() {
			return "Falha ao computador voto do eleitor. Verifique os logs";
		}
	},
	FALHA_CHECAR_CPF {
		@Override
		public String getErrorBusinessMessage() {
			return "Falha ao checar CPF do eleitor. Verifique os logs";
		}
	};

	public abstract String getErrorBusinessMessage();
}
