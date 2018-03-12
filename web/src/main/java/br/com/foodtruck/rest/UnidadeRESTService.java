package br.com.foodtruck.rest;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;

import br.com.foodtruck.model.persitent.Unidade;
import br.com.foodtruck.service.UnidadeMicroServices;

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
 * @see UnidadeMicroServices
 * @since 1.0
 * @version 1.0.0
 */

@Path("/unidade")
@RequestScoped
public class UnidadeRESTService {

	@Inject
	private UnidadeMicroServices service;

	@Context
	private SecurityContext secContext;

	@Context
	private HttpHeaders header;

	@Context
	private ServletContext contexto;

	/**
	 * Recupera lista de unidades
	 * 
	 * @return lista de unidades
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PermitAll
	public List<Unidade> listAll() {
		return service.listAllUnidades();
	}

	/**
	 * Salva a unidade
	 * 
	 * @param unidade
	 * @return response
	 * @throws Exception
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response create(@Valid Unidade unidade, @QueryParam("idEmpresa") long idEmpresa) {
		service.create(unidade, idEmpresa);
		return Response.created(null).build();
	}

	/**
	 * Recupera unidade pelo ID
	 * 
	 * @param id
	 * @return unidade
	 */
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Unidade findById(@PathParam("id") final Long id) {
		return service.findById(id);
	}

}