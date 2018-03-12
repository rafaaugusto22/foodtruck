#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import ${package}.service.exteno.oidc.OpenIDCService;


@Path("/public")
public class Autenticador {

	private static final Logger log = Logger.getLogger(Autenticador.class.getName());

	@Context
	private HttpServletRequest httpRequest;
	
	@Context
	private HttpServletResponse httpResponse;
	
	@Inject
	OpenIDCService openIDservice;

	
	/**
	 * Metodo responsável por recuperar os <b>tokens</b> 
	 * access_token e o id_token a partir do código gerado pelo Identity Provider 
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <p>
	 * 
	 * @author c112141
	 * @return Retorna tokens(access_token e id_token) para aplicação.
	 * @param
	 * @since 1.0
	 **/
	@GET
	@Path("/token/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authentication(@PathParam("code") String code) throws IOException {
		String token = openIDservice.getToken(httpRequest,code);
		JSONObject jwt = new JSONObject(token);
		if(jwt.has("error")){
			log.severe("======================[ LOGIN FALHOU | causa: " + jwt.get("error_description"));
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		return Response.ok(token).build();
	}
	
	
	/**
	 * Metodo responsável por realizar o <b>logout</b> do usuário a partir 
	 * id_token do usuário.
	 * <p>
	 * VERBO HTTP UTILIZADO: GET.
	 * <p>
	 * 
	 * @author c112141
	 * @param
	 * @since 1.0
	 **/
	@GET
	@Path("/logout/{idToken}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@PathParam("idToken") String idToken) throws Exception {
		Response response = openIDservice.logout(idToken);
		return response.ok().build();
	}

}