#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.jms_1_1;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import ${package}.util.system.Messages;

/**
 * Classe responsável por processar as mensagens recebidas remotamente via JMS de forma assincrona.
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
		@ActivationConfigProperty(propertyName = "hostName", propertyValue = "${symbol_dollar}{and.mdb.mq.hostName}"),
		@ActivationConfigProperty(propertyName = "port", propertyValue = "${symbol_dollar}{and.mdb.mq.port}"),
		@ActivationConfigProperty(propertyName = "channel", propertyValue = "${symbol_dollar}{and.mdb.mq.channel}"),
		@ActivationConfigProperty(propertyName = "queueManager", propertyValue = "${symbol_dollar}{and.mdb.mq.queueManager}"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "${symbol_dollar}{and.mdb.mq.destination}"),
		@ActivationConfigProperty(propertyName = "transportType", propertyValue = "${symbol_dollar}{and.mdb.mq.transportType}"),
		})
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class JMSRemoteConsumerMDBService implements MessageListener {

	private static final Logger log = Logger.getLogger(JMSRemoteConsumerMDBService.class.getName());

	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage tm = (TextMessage) message;
			log.info("Mensagem recebida: "+tm.getText());			
		} catch (JMSException e) {
			log.severe(Messages.getString("MSG_ERRO_MENSAGEM") + " | causa: " + e);
		} catch (Exception e) {
			log.severe(Messages.getString("MSG_ERRO_MENSAGEM") + " | causa: " + e);
		}
	}

}
