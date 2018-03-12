package br.com.foodtruck.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.foodtruck.util.interceptors.rest.RESTExceptionInterceptors;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RESTCorsFilter.class);
		classes.add(EmpresaRESTService.class);
		classes.add(RESTExceptionInterceptors.class);
		classes.add(UnidadeRESTService.class);
		classes.add(EmpregadoRESTService.class);
		classes.add(UploadRESTService.class);


		return classes;
	}

}