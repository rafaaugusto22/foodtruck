#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util.exceptions;

public class RequestTimeOutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestTimeOutException(String message) {
		super(message);
	}

}