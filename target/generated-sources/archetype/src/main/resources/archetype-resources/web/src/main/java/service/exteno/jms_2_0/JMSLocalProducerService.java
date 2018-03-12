#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.jms_2_0;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;

/**
 * 
 * Classe responsável por enviar mensagens via JMS 2.0. 
 * 
 * 
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 */
@Stateless
public class JMSLocalProducerService {
	
	@Inject
	@JMSConnectionFactory("jms/foodtruckConnectionFactory")	
	JMSContext context;
	 
	@Resource(mappedName = "java:/jms/${parentArtifactId}Queue")
	Destination ${parentArtifactId}Queue;
	
	@Resource(mappedName = "java:/jms/${parentArtifactId}AsyncQueue")
	Destination ${parentArtifactId}AsyncQueue;
	 
	public void sendLocalMessage(String mensagem) {
	  context.createProducer().send(${parentArtifactId}Queue, mensagem);
	}
	
	public void sendLocalAsyncMessage(String mensagem) {
	  context.createProducer().send(${parentArtifactId}AsyncQueue, mensagem);
	}
	
}
