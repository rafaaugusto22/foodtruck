#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.jms_2_0;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;

/**
 * Classe responsável por consumir as mensagens enviadas a fila local sem a utulização de um listener (MDB), utilizando ActiveMQ do próprio Jboss. 
 * 
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 */
@Stateless
public class JMSLocalConsumerService {
	
	@Inject
	@JMSConnectionFactory("jms/foodtruckLocalConnectionFactory")
	private JMSContext context;
	 
	@Resource(lookup = "java:/jms/${parentArtifactId}Queue")
	Destination ${parentArtifactId}Queue;
	 
	public String receiveMessage() {
	  JMSConsumer consumer = context.createConsumer(${parentArtifactId}Queue);
	  return consumer.receiveBody(String.class);
	}
	
}
