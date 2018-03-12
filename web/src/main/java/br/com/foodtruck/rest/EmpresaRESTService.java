package br.com.foodtruck.rest;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Asynchronous;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.com.foodtruck.model.persitent.Empresa;
import br.com.foodtruck.service.EmpresaMicroServices;
import br.com.foodtruck.util.exceptions.DataNotFoundException;

/**
 * Classe responsável por prover os recursos referentes a empresa.
 *
 * @author 
 * @see JaxRsActivator
 * @see EmpresaMicroServices
 * @since 1.0
 * @version 1.0.0
 */
@Path("/empresa")
@RequestScoped
public class EmpresaRESTService {

	private static final Logger log = Logger.getLogger(EmpresaRESTService.class.getName());

	@Inject
	private EmpresaMicroServices services;

	@Context
	private SecurityContext secContext;

	@Context
	private HttpHeaders header;

	@Context
	private ServletContext contexto;

	/**
	 * Metodo responsável por retornar uma lista de objetos do tipo Empresa.
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <p>
	 * {@code @Produces} define o tipo de dado (JSON e XML) gerado pela
	 * interface, desta forma basta inserir <b>Accept: application/json</b> no
	 * cabeçalho HTTP para receber o arquivo no formato JSON.
	 * <p>
	 * Exemplo da consulta <b>/</b>
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
	@RolesAllowed({ "GERENTE", "ANALISTA" })
	public Response getEmpresas() {
		return Response.ok(services.listAllEmpresas()).build();
	}

	/**
	 * Metodo responsável por retornar um objeto do tipo Empresa a partir do ID.
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <p>
	 * {@code @Produces} define o tipo de dado (JSON e XML) gerado pela
	 * interface, desta forma basta inserir <b>Accept: application/json</b> no
	 * cabeçalho HTTP para receber o arquivo no formato JSON.
	 * <p>
	 * Exemplo da consulta <b>/10</b>
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
	@Path("/{id}")
	@RolesAllowed({ "GESTOR" })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getEmpresaById(@PathParam("id") Long id) {
		return Response.ok(services.findEmpresaByID(id)).build();
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
	 * @return Lista de objetos no formato JSON ou XML de acordo com o
	 *         HEADER/ACCEPT da requisição.
	 * @throws Exception
	 * @throws DataNotFoundException
	 * @throws WebApplicationException
	 * @param
	 * @since 1.0
	 **/
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/pagination")
	public Response listEmpresaWithPagination(@QueryParam("inicio") @DefaultValue(value = "0") int inicio,
			@QueryParam("fim") @DefaultValue(value = "10") int fim) {
		return Response.ok(services.listByPagination(inicio, fim)).build();
	}

	/**
	 * Metodo responsável por retornar uma lista de objetos <b>com paginação</b>
	 * a partir de um <b>filtro</b>
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
	 * Exemplo da consulta
	 * <b>/pagination?inicio=10&fim=30&filtro=valorQualquer</b>
	 * <p>
	 * Disponibilizamos como versão 2 /pagination<b>/v2</b> para servir como
	 * exemplo, caso haja adição de funcionalidades a um recurso já disponível.
	 * 
	 * @author 
	 * @return Lista de objetos no formato JSON ou XML de acordo com o
	 *         HEADER/ACCEPT da requisição.
	 * @throws Exception
	 * @throws DataNotFoundException
	 * @throws WebApplicationException
	 * @param
	 * @since 1.0
	 **/
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/pagination/v2")
	public Response listEmpresaWithPaginationAndFilter(@QueryParam("inicio") @DefaultValue(value = "0") int inicio,
			@QueryParam("fim") @DefaultValue(value = "10") int fim, @QueryParam("filtro") String filtro,
			@QueryParam("coluna") String coluna) {
		return Response.ok(services.listByPaginationAndFilter(inicio, fim, filtro, coluna)).build();
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
	public Response getEmpresaXML(@PathParam("resourceID") long resourceID) {
		return Response.ok(services.findEmpresaByID(resourceID)).type(MediaType.APPLICATION_XML).build();
	}

	/**
	 * Metodo responsável retornar um arquivo no formato JSON, pela extensão
	 * inserida na requisição
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <p>
	 * {@code @Produces} define o tipo de dado (JSON) gerado pela interface,
	 * <b>É NECESSÁRIO</b> inserir Accept: application/json no cabeçalho HTTP
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
	public Response getEmpresaJSON(@PathParam("resourceID") long resourceID) {
		return Response.ok(services.findEmpresaByID(resourceID)).type(MediaType.APPLICATION_JSON_TYPE).build();
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
	 * <p>
	 * O uso do <b>@Valid é obrigatório </b>para que o Bean Validation seja
	 * executado no Objeto.
	 * 
	 * @author 
	 * @return Objeto JSON ou XML de acordo com o HEADER/ACCEPT da requisição.
	 * @throws Exception
	 * @throws DataNotFoundException
	 * @throws WebApplicationException
	 * @since 1.0
	 * @see @Valid
	 * @param Objeto
	 *            Empresa
	 **/
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response create(@Valid Empresa empresa) throws Exception {
		services.create(empresa);
		return Response.created(new URI("/empresa/" + empresa.getCnpj())).build();
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
	public Response updateEmpresa(@Valid Empresa empresa) {
		services.update(empresa);
		return Response.status(Response.Status.ACCEPTED).entity(empresa).build();
	}

	/**
	 * Método possibilita parametrizar o retorno esperado, especificando quais
	 * campos do objeto são desejados.
	 * <p>
	 * Exemplo da consulta /query/2?parametros=coCnpj onde /2 é o ID o objeto,
	 * ?parametros são os parâmetros desejados
	 * 
	 * @author 
	 * @see @FormParam
	 * @since 1.0
	 * @param
	 */
	@GET
	@Path("/cnpj/{cnpj}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getEmpresaByCnpj(@Size(min = 14, max = 15) @PathParam("cnpj") String cnpj) {
		Response.ResponseBuilder responseBuilder = Response.ok(services.findByCnpj(cnpj));
		return responseBuilder.build();
	}

	/**
	 * Método possibilita parametrizar o retorno esperado, especificando quais
	 * campos do objeto são desejados.
	 * <p>
	 * Exemplo da consulta /query/2?parametros=coCnpj onde /2 é o ID o objeto,
	 * ?parametros são os parâmetros desejados
	 * 
	 * @author 
	 * @see @FormParam
	 * @since 1.0
	 * @param
	 */
	@GET
	@Path("/query/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getEmpresaByQuery(@PathParam("id") long id, @NotNull @QueryParam("parametros") String params)
			throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok(services.findEmpresaByIDWithQuery(id, params));
		return responseBuilder.build();
	}

	/**
	 * Método reponsável por capturar um parâmetro específico do formulário.
	 * <p>
	 * O(s) campo(s) são mapeados pela {@code tag html name} do formulário.
	 * 
	 * @author 
	 * @see @FormParam
	 * @since 1.0
	 * @param form
	 */
	@POST
	@Path("/form")
	@Consumes("application/x-www-form-urlencoded")
	public void getFormValues(@FormParam("coCnpj") String coCnpj) {
		log.info(coCnpj);
	}

	/**
	 * Método reponsável por gerar um mapa {@code MultivaluedMap<Key, Value>}
	 * com todos os parâmetros do formulário.
	 * <p>
	 * Os campos são mapeados pela {@code tag html name} do formulário.
	 * 
	 * @author 
	 * @see MultivaluedMap
	 * @since 1.0
	 * @param formParams
	 */
	@POST
	@Path("/formparms")
	@Consumes("application/x-www-form-urlencoded")
	public void getAllFormValues(MultivaluedMap<String, String> formParams) {
		log.info(formParams.getFirst("coCnpj"));
		log.info(formParams.getFirst("noRazaoSocial"));
	}

	/**
	 * Método reponsável por recuperar a empresa e colocá-la em cache.
	 * <p>
	 * 
	 * @author 
	 * @since 1.0
	 * @param id
	 */
	@GET
	@Path("/cache/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PermitAll
	public Response getEmpresaCache(@PathParam("id") long id) {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(10);
		cacheControl.setPrivate(true);
		Empresa empresa = services.findEmpresaByID(id);
		Response.ResponseBuilder responseBuilder = Response.ok(empresa).type(MediaType.APPLICATION_JSON_TYPE);
		responseBuilder.cacheControl(cacheControl);
		return responseBuilder.build();
	}

	/**
	 * Método reponsável por hash para revalidação do cache.
	 * <p>
	 * 
	 * @author 
	 * @since 1.0
	 * @param id
	 * @param request
	 */
	@GET
	@Path("/etag/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getEmpresaWithEtag(@PathParam("id") long id, @Context Request request) {
		Empresa empresa = services.findEmpresaByID(id);
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(3600);
		cacheControl.setPrivate(true);
		EntityTag et = new EntityTag("123456789");
		Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(et);
		if (responseBuilder != null) {
			responseBuilder.build();
		}
		responseBuilder = Response.ok(empresa);
		responseBuilder.cacheControl(cacheControl);
		return responseBuilder.tag(et).build();
	}

	/**
	 * Método reponsável por recuperar a empresa de forma assíncrona.
	 * <p>
	 * 
	 * @author 
	 * @since 1.0
	 * @param ar
	 * @param id
	 */
	@GET
	@Path("/async/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Asynchronous
	public void getEmpresaByIdAsynchronous(@Suspended final AsyncResponse ar, @PathParam("id") final long id) {
		ar.setTimeout(5, TimeUnit.SECONDS);
		ar.setTimeoutHandler(new TimeoutHandler() {
			@Override
			public void handleTimeout(AsyncResponse ar) {
				ar.resume(Response.status(Response.Status.REQUEST_TIMEOUT).build());
				log.info("TIMEOUT atingido");
			}
		});

		new Thread() {
			@Override
			public void run() {
				Empresa empresa = null;
				try {
					Thread.sleep(3000);
					empresa = services.findEmpresaByID(id);
					ar.resume(empresa);
				} catch (InterruptedException e) {
					ar.resume(e);
					return;
				}
				Response response = Response.ok(empresa).build();
				ar.resume(response);
			}
		}.start();

	}

}