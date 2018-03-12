#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ${package}.service.exteno.jms_1_1.JMSAdapterService;
import ${package}.service.exteno.jms_1_1.JMSRemoteConsumerMDBService;
import ${package}.service.exteno.jms_1_1.JMSRemoteConsumerService;
import ${package}.service.exteno.jms_1_1.JMSRemoteProducerService;
import ${package}.service.exteno.jms_2_0.JMSLocalConsumerMDBService;
import ${package}.service.exteno.jms_2_0.JMSLocalConsumerService;
import ${package}.service.exteno.jms_2_0.JMSLocalProducerService;

	/**
	 * Classe responsável por receber e enviar mensagens via JMS. 
	 * Está sendo utilizado o JMS 1.1 para acessar remotamente o servidor IBM MQ, 
	 * pois até a criação desse archetype o servidor IBM MQ não suportava chamadas via JMS 2.0. 
	 * Para chamadas locais está sendo utilizado o JMS 2.0 junto com o ActiveMQ do próprio JBOSS.
	 *
	 * @author c112141
	 * @see JMSAdapterService
	 * @see JMSLocalConsumerService	
	 * @see JMSLocalProducerService
	 * @see JMSLocalConsumerMDBService
	 * @since 1.0
	 * @version 1.0.0
	 */
	@Path("/jms")
	@RequestScoped
	public class JmsRESTService {		
		
		@Inject
		private JMSLocalConsumerService localConsumer;
		
		@Inject
		private JMSAdapterService jmsService;
		
		@Inject
		private JMSLocalProducerService localProducer;

		@Context
		private SecurityContext secContext;

		@Context
		private HttpHeaders header;

		@Context
		private ServletContext contexto;
		
		
		/**
		 * Método responsável por enviar uma mensagem a fila, no qual a mensagem será consumida por cada requisição localmente, utilizando JMS 2.0.
		 * @param message
		 * @return Response
		 */
		@POST
		@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		@Path("simpleLocalMessage")
		public Response sendLocalSimpleMessage(String message) {
			localProducer.sendLocalMessage(message);
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		}
		
		/**
		 * Método responsável por enviar a uma mensagem a fila, no qual a mensagem será consumida via listener (MDB) localmente, utilizando JMS 2.0.
		 * @param message
		 * @return Response
		 */
		@POST
		@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		@Path("localAsyncMessage")
		public Response sendLocalAsyncMessage(String message) {
			localProducer.sendLocalAsyncMessage(message);
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		}
		
		/**
		 * Método responsável por requisitar as mensagens, uma de cada vez, utilizando JMS 2.0.
		 * @return
		 */
		@GET		
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		@Path("simpleLocalMessage")
		public Response getSimpleLocalMessage() {
			return Response.ok(localConsumer.receiveMessage()).build();
		}
		

		/**
		 * Método responsável por enviar uma mensagem a fila e receber a mensagem da fila de resposta, utilizando JMS 1.1.
		 * @param message
		 * @return Response
		 */
		@POST
		@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		@Path("defaultMessage")
		public Response sendDefaultMessage(String message) {
			jmsService.sendMessageDefault(message);
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		}
		
	}
		
	

