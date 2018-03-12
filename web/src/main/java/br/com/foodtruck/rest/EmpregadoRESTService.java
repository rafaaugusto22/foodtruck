package br.com.foodtruck.rest;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;

import br.com.foodtruck.model.persitent.Empregado;
import br.com.foodtruck.service.EmpregadoMicroServices;
import br.com.foodtruck.service.EmpresaMicroServices;
import br.com.foodtruck.util.exceptions.DataNotFoundException;

/**
 * Define o contrato entre the contract between a returned instance and the
 * runtime when an application needs to provide meta-data to the runtime.
 * <p>
 * An application class should not extend this class directly. {@code Response}
 * class is reserved for an extension by a JAX-RS implementation providers. An
 * application should use one of the static methods to create a {@code Response}
 * instance using a ResponseBuilder.
 * </p>
 * <p>
 * Several methods have parameters of type URI, {@link UriBuilder} provides
 * convenient methods to create such values as does
 * {@link URI#create(java.lang.String)}.
 * </p>
 *
 * @author 
 * @see JaxRsActivator
 * @see EmpresaMicroServices
 * @since 1.0
 * @version 1.0.0
 */

@Path("/empregado")
@RequestScoped
public class EmpregadoRESTService {

	@Inject
	private EmpregadoMicroServices services;

	@Context
	private SecurityContext secContext;

	@Context
	private HttpHeaders header;

	@Context
	private ServletContext contexto;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Empregado> listAllEmpregados() {
		return services.listAllEmpregados();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getEmpregadoById(@PathParam("id") Long id) {
		ResponseBuilder responseBuilder = Response.ok(services.findEmpregadoByID(id));
		return responseBuilder.build();
	}

	/**
	 * Metodo responsável por retornar uma lista de objetos <b>com paginação</b>
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <P>
	 * A Annotation {@code @DefaultValue} seta um valor default para o método,
	 * no caso 0-10, caso o parâmetro não seja passada no chamada
	 * <p>
	 * {@code @Produces} define o tipo de dado (JSON e XML) gerado pela
	 * interface, desta forma basta inserir <b>Accept: application/json</b> no
	 * cabeçalho HTTP para receber o arquivo no formato JSON.
	 * <p>
	 * Exemplo da consulta <b>/pagination?inicio=10&fim=30</b>
	 * 
	 * @author 
	 * @return Objeto JSON ou XML de acordo com o HEADER/ACCEPT da requisição.
	 * @throws Exception
	 * @throws DataNotFoundException
	 * @throws WebApplicationException
	 * @param
	 * @since 1.0
	 **/
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/pagination")
	public Response listEmpregadoWithPagination(@QueryParam("inicio") @DefaultValue(value = "0") int inicio,
			@QueryParam("fim") @DefaultValue(value = "10") int fim) {
		Response.ResponseBuilder responseBuilder = Response.ok(services.listByPagination(inicio, fim));
		return responseBuilder.build();
	}

	/**
	 * Metodo responsável retornar um arquivo no formato XML, pela extensão
	 * inserida na requisição
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <p>
	 * {@code @Produces} define o tipo de dado (XML) gerado pela interface,
	 * <b>NÃO NECESSITA</b> inserir Accept: application/xml no cabeçalho HTTP
	 * para receber o arquivo no formato XML.
	 * <p>
	 * Exemplo da consulta /XYZ.<b>xml</b> , XYZ é o IDENTIFICADOR (ID) e .xml é
	 * o formato desejado
	 * 
	 * @author 
	 * @return Objeto formato XML.
	 * @throws Exception
	 * @throws DataNotFoundException
	 * @throws WebApplicationException
	 * @since 1.0
	 * @param
	 **/
	@Path("{resourceID}.xml")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	public Response getEmpregadoXML(@PathParam("resourceID") long resourceID) {
		Response.ResponseBuilder responseBuilder = Response.ok(services.findEmpregadoByID(resourceID))
				.type(MediaType.APPLICATION_XML);
		return responseBuilder.build();
	}

	/**
	 * Metodo responsável retornar um arquivo no formato JSON, pela extensão
	 * inserida na requisição
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <p>
	 * {@code @Produces} define o tipo de dado (JSON) gerado pela interface,
	 * <b>NÃO NECESSITA</b> inserir Accept: application/json no cabeçalho HTTP
	 * para receber o arquivo no formato JSON.
	 * <p>
	 * Exemplo da consulta /XYZ.<b>json</b> , onde XYZ é o IDENTIFICADOR (ID) e
	 * .json é o formato desejado
	 * 
	 * @author 
	 * @return Objeto no formato JSON.
	 * @throws Exception
	 * @throws DataNotFoundException
	 * @throws WebApplicationException
	 * @since 1.0
	 * @param
	 **/
	@Path("{resourceID}.json")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getEmpregadoJSON(@PathParam("resourceID") long resourceID) {
		Response.ResponseBuilder responseBuilder = Response.ok(services.findEmpregadoByID(resourceID))
				.type(MediaType.APPLICATION_JSON_TYPE);
		return responseBuilder.build();
	}

	/**
	 * Metodo responsável por criar um recurso (Empresa)
	 * <p>
	 * VERBO HTTP UTILIZADO: POST.
	 * <P>
	 * A Annotation {@code @Valid } faz a validação do objeto em tempo de
	 * execução.
	 * <p>
	 * {@code @Consumes} define o tipo de dado (JSON e XML) aceito na chamada.
	 * <p>
	 * {@code @Produces} define o tipo de dado (JSON e XML) gerado pela
	 * interface, desta forma basta inserir <b>Accept: application/json</b> no
	 * cabeçalho HTTP para receber o arquivo no formato JSON.
	 * 
	 * @author 
	 * @return Objeto JSON ou XML de acordo com o HEADER/ACCEPT da requisição.
	 * @throws Exception
	 * @throws DataNotFoundException
	 * @throws WebApplicationException
	 * @since 1.0
	 * @param
	 **/
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response create(@Valid Empregado empregado) {
		services.create(empregado);
		return Response.status(Response.Status.CREATED).entity(empregado).build();
	}

	/**
	 * Método responsável pela atualização do recurso.
	 * <p>
	 * VERBO HTTP UTILIZADO: PUT.
	 * <p>
	 * Neste exemplo a chamada passa o objeto Javascript EMPRESA, desta forma a
	 * conversão acontece de forma automática.
	 * <p>
	 * A Annotation {@code @Valid } faz a validação do objeto em tempo de
	 * execução.
	 * <p>
	 * {@code @Consumes} define o tipo de dado (JSON e XML) aceito na chamada.
	 * <p>
	 * {@code @Produces} define o tipo de dado (JSON e XML) gerado pela
	 * interface, desta forma basta inserir Accept: application/json no
	 * cabeçalho HTTP para receber o arquivo no formato JSON.
	 * 
	 * @author 
	 * @see @FormParam
	 * @since 1.0
	 * @param
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateEmpregado(@Valid Empregado empregado) {
		services.update(empregado);
		return Response.status(Response.Status.ACCEPTED).entity(empregado).build();
	}

}