#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.jms_2_0;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.ResourceAdapter;

import ${package}.util.system.Messages;

/**
 * Classe responsável por processar as mensagens recebidas localmente via JMS de forma assincrona, utilizando ActiveMQ do próprio Jboss.
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/${parentArtifactId}AsyncQueue"),
		})
@ResourceAdapter(value = "activemq-ra.rar")
public class JMSLocalConsumerMDBService implements MessageListener {

	private static final Logger log = Logger.getLogger(JMSLocalConsumerMDBService.class.getName());

	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage tm = (TextMessage) message;
			log.info("Mensagem recebida: "+tm.getText());			
		} catch (JMSException ex) {
			log.severe(Messages.getString("MSG_ERRO_MENSAGEM") + " | causa: " + ex);
		}
	}

}
