package br.com.foodtruck.util.exceptions;

public class NotAcceptableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotAcceptableException(String message) {
		super(message);
	}

}