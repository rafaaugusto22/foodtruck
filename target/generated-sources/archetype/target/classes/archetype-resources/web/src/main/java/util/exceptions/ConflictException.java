#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util.exceptions;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String message) {
		super(message);
	}

}