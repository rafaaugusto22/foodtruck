package br.com.foodtruck.service.exteno;

import java.util.logging.Logger;

/**
 * Classe responsável por simular uma interface de CADASTRAMENTO NO FGTS LÓGICO.
 * 
 * @author 
 * @since 1.0
 * @version 1.0
 *
 */
public class FGTSService {

	private static final Logger log = Logger.getLogger(FGTSService.class.getName());

	public FGTSService() {
		geraFgts();
	}

	private void geraFgts() {
		log.info("Iniciando processo de cadastramento no FGTS");
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Processde de cadastramento no FGTS -> CONCLUÍDO");
	}

}