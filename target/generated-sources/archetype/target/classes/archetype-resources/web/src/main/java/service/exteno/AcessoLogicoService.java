#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno;

import java.util.logging.Logger;

/**
 * Classe responsável por simular uma interface de CADASTRAMENTO NO ACESSO
 * LÓGICO.
 * 
 * @author 
 * @since 1.0
 * @version 1.0
 *
 */
public class AcessoLogicoService {

	private static final Logger log = Logger.getLogger(AcessoLogicoService.class.getName());

	public AcessoLogicoService() {
		geraCadastroAcessoLogico();
	}

	private void geraCadastroAcessoLogico() {
		log.info("Iniciando processo de cadastramento no acesso lógico");
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		log.info("Processde de cadastramento no acesso lógico -> CONCLUÍDO");
	}

}