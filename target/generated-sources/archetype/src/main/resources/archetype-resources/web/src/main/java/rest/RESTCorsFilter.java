#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

/**
 * Cross-origin resource sharing (CORS)(ou compartilhamento de recursos de
 * origem cruzada) é uma especificação de uma tecnologia de navegadores que
 * define meios para um servidor possa permitir que seus recursos sejam
 * acessados por uma página web de um domínio diferente. Esse tipo de acesso
 * seria de outra forma negado pela same origin policy.
 * <p>
 * CORS define um meio pelo qual um navegador e um servidor web podem interagir
 * para determinar se devem ou não utilizar/permitir requisições cross-origem.
 * 
 * <p>
 * É um acordo que permite grande flexibilidade, mas é mais seguro que permitir
 * todos as requisições desse tipo.
 * <p>
 * Para maiores informações, clique <a href=
 * "https://docs.jboss.org/resteasy/docs/3.0.9.Final/javadocs/org/jboss/resteasy/plugins/interceptors/CorsFilter.html">
 * aqui </a>
 * 
 * @author 
 * @see CorsFilter
 */
@Provider
@PreMatching
@Priority(Priorities.AUTHORIZATION)
public class RESTCorsFilter implements Feature, ContainerRequestFilter, ContainerResponseFilter {

	private static final Logger log = Logger.getLogger(RESTCorsFilter.class.getName());

	@Override
	public boolean configure(FeatureContext context) {
		CorsFilter corsFilter = new CorsFilter();
		corsFilter.getAllowedOrigins().add("*");
		context.register(corsFilter);
		return true;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.info("PASSOU NA CLASSE CORS");
		log.info(requestContext.getHeaderString("User-Agent"));
		log.info(requestContext.getHeaderString("Accept-Language"));
		log.info(requestContext.getHeaderString("Accept"));
		log.info(requestContext.getHeaderString("Accept-Encoding"));
		log.info(requestContext.getHeaderString("Connection"));
		log.info(requestContext.getHeaderString("Host"));
		log.info(requestContext.getHeaderString("Authorization"));
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
	}

}