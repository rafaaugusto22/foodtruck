#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.jms_1_1;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

import ${package}.service.exteno.ged.GEDService;

/**
 * 
 * Classe responsável por enviar mensagens via JMS 1.1. 
 * 
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 */
@Stateless
public class JMSRemoteProducerService {
	
	
	@Resource(mappedName = "java:/jms/foodtruckConnectionFactory")
	private QueueConnectionFactory queueConnectionFactory;
 
    private static final long JMS_TIMEOUT = Long.parseLong(System.getProperty("and.mdb.mq.timeout"));
    
    private static final Logger log = Logger.getLogger(GEDService.class.getName());
    
    @Inject
	JMSRemoteConsumerService consumer;
	

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
	
	
	 /**
	  * Método responsável por enviar mensagens via JMS.
	  * 
	 * @param mensagem
	 * @param reqQueue
	 * @param rspQueue
	 * @return String
	 * @throws JMSException
	 */
	public String sendRemoteMessage(String mensagem,Queue reqQueue, Queue rspQueue) throws JMSException {
    	String result ="";
		TextMessage textMessage;
		try{			
			MessageProducer messageProducer = session.createProducer(reqQueue);
			messageProducer.setTimeToLive(JMS_TIMEOUT);
			textMessage = session.createTextMessage(mensagem.toString());
			textMessage.setJMSExpiration(JMS_TIMEOUT);
			messageProducer.send(textMessage);
			
			log.finest("Mensagem enviada");

			String idMessage = textMessage.getJMSMessageID();

			log.finest("MessageID: " + idMessage);

			result = consumer.receiveMessage(idMessage,rspQueue);
		
		} catch (JMSException e) {
			log.severe("Erro na requisição de integração JMS.");
			e.printStackTrace();
			throw e;
		}
		return result;
    }
    
    
	
}
