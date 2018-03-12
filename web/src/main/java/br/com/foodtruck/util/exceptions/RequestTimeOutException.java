package br.com.foodtruck.util.exceptions;

public class RequestTimeOutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestTimeOutException(String message) {
		super(message);
	}

}