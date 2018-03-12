#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nimbusds.jwt.JWTClaimsSet;

import ${package}.service.exteno.oidc.OpenIDCService;
import ${package}.util.system.ConfigApplication;

/**
 * Classe responsável por verificar processar as chamadas via Rest. 
 * @author c112141
 *
 */
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class PreMatchingAuthFilter implements ContainerRequestFilter {

	private static final Logger log = Logger.getLogger(PreMatchingAuthFilter.class.getName());

	@Context
	private SecurityContext secContext;

	@Context
	private ServletContext contexto;

	@Context
	private HttpServletRequest httpRequest;

	@Context
	private SessionContext ctx;

	@Inject
	private OpenIDCService openIdConnect;

	public void filter(ContainerRequestContext reqCtx) throws IOException {
		log.info("PreMatchingAuthFilter: Iniciando o filtro de autenticação baseada em token para "
				+ reqCtx.getUriInfo().getPath());
		if (reqCtx.getUriInfo().getPath().startsWith("/public")) {
			return;
		}

		String authHeaderVal = reqCtx.getHeaderString("Authorization");

		// verifica se existe um access_token e se está ativo 
		if (authHeaderVal != null 
				&& authHeaderVal.startsWith("Bearer")
				&& openIdConnect.isActiveToken(authHeaderVal.split(" ")[1])) {
			try {
				log.info("Valida assinatura do token");
				log.info("access_token:${symbol_escape}n" + authHeaderVal.split(" ")[1]);
				final JWTClaimsSet claim = openIdConnect.validate(authHeaderVal.split(" ")[1]);
				
				if (claim.getClaim("preferred_username") != null) {

					Set<String> roles = new HashSet<>();
					addRoles(claim, roles);
					Authorizer authorizer = new Authorizer(roles, claim.getClaim("preferred_username").toString(),
							secContext.isSecure(), claim.getClaim("typ").toString());
					reqCtx.setSecurityContext(authorizer);
				}
			} catch (Exception ex) {
				log.info("Token inválido");
				reqCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

			}

		} else {
			log.info("Token não está ativo!");			
			reqCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	public static class Authorizer implements SecurityContext {

		Set<String> roles;
		String username;
		boolean isSecure;
		String authenticationScheme;

		public Authorizer(Set<String> roles, final String username, boolean isSecure, String authenticationScheme) {
			this.roles = roles;
			this.username = username;
			this.isSecure = isSecure;
			this.authenticationScheme = authenticationScheme;
		}

		@Override
		public Principal getUserPrincipal() {
			return new User(username);
		}

		@Override
		public boolean isUserInRole(String role) {
			return roles.contains(role);
		}

		@Override
		public boolean isSecure() {
			return isSecure;
		}

		@Override
		public String getAuthenticationScheme() {
			return authenticationScheme;
		}
	}

	public static class User implements Principal {
		String name;

		public User(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	/**
	 * Método responsável por identificar as Roles passadas no access_token.
	 * 
	 * @param claim
	 * @param roles
	 */
	private void addRoles(JWTClaimsSet claim, Set<String> roles) {
		try {
			JSONObject jsonRolesResource = new JSONObject(claim.getClaim("resource_access").toString());
			JSONObject jsonRolesResourceClient = new JSONObject(jsonRolesResource.get(ConfigApplication.getSystemProperties(ConfigApplication.OIDC_CLIENT)).toString());
			JSONArray jsonArrRolesResourceCleint = jsonRolesResourceClient.getJSONArray("roles");

			for (int i = 0; i < jsonArrRolesResourceCleint.length(); i++) {
				roles.add(jsonArrRolesResourceCleint.getString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			JSONObject jsonRolesRealm = new JSONObject(claim.getClaim("realm_access").toString());
			JSONArray jsonArrRolesRealm = jsonRolesRealm.getJSONArray("roles");

			for (int i = 0; i < jsonArrRolesRealm.length(); i++) {
				roles.add(jsonArrRolesRealm.getString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
