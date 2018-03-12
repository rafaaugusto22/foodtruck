package br.com.foodtruck.util.exceptions;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.foodtruck.util.interceptors.rest.RESTExceptionInterceptors;

/**
 * Classe responsável por mapear os dados que retornarão em caso de erro.
 * <p>
 * <b>code</b> Código do erro no padrão HTTP;
 * <p>
 * <b>message</b> Mensagem para o usuário;
 * <p>
 * <b>detail</b> Mensagem de erro da aplicação, coletada da Exception
 * 
 * @author 
 * @since 1.0
 * @version 1.1.0
 * @see RESTExceptionInterceptors
 *
 */
@XmlRootElement
public class ResourceError {

	private int code;
	private String message;
	private String detail;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public ResourceError() {

	}
}