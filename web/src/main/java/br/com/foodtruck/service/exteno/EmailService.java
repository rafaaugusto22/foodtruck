package br.com.foodtruck.service.exteno;

import java.util.logging.Logger;

/**
 * Classe responsável por simular uma interface de CRIAÇÃO DE E-MAIL.
 * 
 * @author 
 * @since 1.0
 * @version 1.0
 *
 */

public class EmailService {

	private static final Logger log = Logger.getLogger(EmailService.class.getName());

	public EmailService() {
		geraEmail();
	}

	private void geraEmail() {
		log.info("Iniciando processo de geração de e-mail");
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Processo de geração de e-mail -> CONCLUÍDO");
	}

}