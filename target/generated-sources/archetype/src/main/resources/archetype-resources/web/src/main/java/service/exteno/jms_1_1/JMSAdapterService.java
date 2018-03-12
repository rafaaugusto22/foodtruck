#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.jms_1_1;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Queue;

import ${package}.util.exceptions.IntegrationException;
import ${package}.util.system.Messages;

/**
 * Classe responsável por adaptar as mensagens e envia-las a suas respectivas filas. 
 * 
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 *
 */
@Stateless
public class JMSAdapterService{	
	
	@Resource(mappedName = "java:/jms/${symbol_dollar}{and.mdb.mq.response}")
	private Queue rspQueue;
	
	@Resource(mappedName = "java:/jms/${symbol_dollar}{and.mdb.mq.destination}")
	private Queue reqQueue;
	
	@Resource(mappedName = "java:/jms/SERVICO.SISGD.REQ")
	private Queue reqQueueGED;

	@Resource(mappedName = "java:/jms/SERVICO.SISGD.GRAVADOCUMENTO")
	private Queue reqQueueGEDGravaDocumento;

	@Resource(mappedName = "java:/jms/SERVICO.SISGD.RSP")
	private Queue rspQueueGED;
	
	@Inject
	private JMSRemoteProducerService producer;
	
	/**
	 * Método responsável por enviar mensagem e recuperar as respostas na fila padrão do sistema.
	 * 
	 * @param mensagem
	 * @return String
	 */
	public String sendMessageDefault(String mensagem){
		String result = "";
		try {
			result = producer.sendRemoteMessage(mensagem, reqQueue, rspQueue);
		} catch (JMSException e) {
			e.printStackTrace();
			throw new IntegrationException("Erro de integração JMS");
		}
		return result;
	}
	
	/**
	 * Método com a finalidade de enviar e receber as mensagens de 
	 * retorno a uma requisição ao serviço do GED. 
	 * 
	 * @param mensagem
	 * @return String
	 */
	public String sendMessageGEDRequest(String mensagem){
		String result = "";
		try {
			result = producer.sendRemoteMessage(mensagem, reqQueueGED, rspQueueGED);
		} catch (JMSException e) {
			e.printStackTrace();
			throw new IntegrationException(Messages.getString("MSG_GED04"));
		}
		return result;
	}
	
	/**
	 * Método com a finalidade de enviar e receber as mensagens de inclusão de documentos. 
	 * 
	 * @param mensagem
	 * @return String
	 */
	public String sendMessageGEDGravaDocumento(String mensagem){
		String result = "";
		try {
			result = producer.sendRemoteMessage(mensagem, reqQueueGEDGravaDocumento, rspQueueGED);
		} catch (JMSException e) {
			e.printStackTrace();
			throw new IntegrationException(Messages.getString("MSG_GED04"));
		}
		return result;
	}
	
}
