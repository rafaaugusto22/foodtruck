#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ${package}.rest.ged.GEDRESTService;
import ${package}.util.interceptors.rest.RESTExceptionInterceptors;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RESTCorsFilter.class);
		classes.add(EmpresaRESTService.class);
		classes.add(Autenticador.class);
		classes.add(RESTExceptionInterceptors.class);
		classes.add(UnidadeRESTService.class);
		classes.add(EmpregadoRESTService.class);
		classes.add(UploadRESTService.class);
		classes.add(PreMatchingAuthFilter.class);
		classes.add(JmsRESTService.class);
		classes.add(GEDRESTService.class);
		return classes;
	}

}