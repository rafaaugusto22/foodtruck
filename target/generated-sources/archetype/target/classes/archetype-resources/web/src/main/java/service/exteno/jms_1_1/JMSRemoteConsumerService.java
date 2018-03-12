#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.jms_1_1;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

import ${package}.util.exceptions.IntegrationException;
import ${package}.util.system.Messages;

/**
 * Classe responsável por consumir as mensagens enviadas a fila de resposta.
 * 
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 */
@Stateless
public class JMSRemoteConsumerService {

	@Resource(mappedName = "java:/jms/foodtruckConnectionFactory")
	private QueueConnectionFactory queueConnectionFactory;	
	
	private static final long JMS_TIMEOUT = Long.parseLong(System.getProperty("and.mdb.mq.timeout"));

	private static final Logger log = Logger.getLogger(JMSRemoteConsumerMDBService.class.getName());
	
	QueueConnection queueConnection = null;
	Session session;
	
	
	@PostConstruct
    public void init() {
        try {
        	queueConnection = queueConnectionFactory.createQueueConnection();
            session = queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
 
    @PreDestroy
    public void destroy() {
        if (queueConnection != null) {
            try {
            	queueConnection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	public String receiveMessage(String jmsMessageID,Queue queue)
			throws JMSException, IntegrationException {

		MessageConsumer messageConsumer = session.createConsumer(queue,
				String.format("JMSCorrelationID='%s'", jmsMessageID));

		queueConnection.start();

		Message message = messageConsumer.receive(JMS_TIMEOUT);
		// verifica mensagem recebida
		if ((message == null) || (!(message instanceof TextMessage))) {
			log.warning("Mensagem recebida "
					+ (message == null ? "é nula" : "não está no formato de texto"));
			;
			throw new IntegrationException(String.format(Messages.getString("MSG_ERRO_MENSAGEM"),
					(message == null ? "é nula" : "não está no formato de texto")));
		} else {
			String result = ((TextMessage) message).getText();
			log.info(result);
			return result;
		}

	}

}
