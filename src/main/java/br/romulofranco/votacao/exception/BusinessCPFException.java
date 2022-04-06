package br.romulofranco.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BusinessCPFException extends ResponseStatusException {

	private final HttpStatus httpStatus;

	public BusinessCPFException(BusinessMessage message, HttpStatus httpStatus) {
		super(httpStatus, message.getErrorBusinessMessage());
		this.httpStatus = httpStatus;
	}

	public int getHttpStatus() {
		return this.httpStatus.value();
	}
}
