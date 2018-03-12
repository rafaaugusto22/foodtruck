#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

import ${package}.model.persitent.Empregado;

/**
 * Classe responsável por simular uma fachada entre as interfaces de
 * CADASTRAMENTO NO ACESSO LÓGICO, CRIAÇÃO DE E-MIAL E REGISTRO NO FGTS.
 * 
 * @author 
 * @since 1.0
 * @version 1.0
 * @see @Observes
 *
 */
@RequestScoped
public class ContratoTrabalhistaFacade {

	private static final Logger log = Logger.getLogger(ContratoTrabalhistaFacade.class.getName());

	public void contratoTrabalhistaFacadeEventObserver(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Empregado empregado) {
		disparaTransacoes();
	}

	@PostConstruct
	public void disparaTransacoes() {
		log.info("CHAMANDO - AcessoLogicoService");
		new AcessoLogicoService();
		log.info("CHAMANDO - EmailService");
		new EmailService();
		log.info("CHAMANDO - FGTSService");
		new FGTSService();
	}

}