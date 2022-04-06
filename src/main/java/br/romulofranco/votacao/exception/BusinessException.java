package br.romulofranco.votacao.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

	private final HttpStatus httpStatus;

	public BusinessException(BusinessMessage businessExceptionMessage, HttpStatus httpStatus) {
		super(businessExceptionMessage.getErrorBusinessMessage(), null, true, false);
		this.httpStatus = httpStatus;
	}

	public int getHttpStatus() {
		return this.httpStatus.value();
	}
}
