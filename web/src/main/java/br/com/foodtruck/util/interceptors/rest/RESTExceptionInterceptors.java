package br.com.foodtruck.util.interceptors.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.GroupDefinitionException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyViolationException;

import br.com.foodtruck.util.exceptions.BadRequestException;
import br.com.foodtruck.util.exceptions.ConflictException;
import br.com.foodtruck.util.exceptions.NotAcceptableException;
import br.com.foodtruck.util.exceptions.RequestTimeOutException;
import br.com.foodtruck.util.exceptions.ResourceError;
//import br.com.foodtruck.util.exceptions.UnauthorizedException;
import br.com.foodtruck.util.system.Messages;

@Provider
public class RESTExceptionInterceptors implements ExceptionMapper<Exception> {

	public Response toResponse(Exception e) {
		ResourceError resourceError = new ResourceError();
		if (e.getCause() instanceof NoResultException) {
			/**
			 * CODIGO 404 - NOT FOUND
			 */
			resourceError.setCode(Response.Status.NOT_FOUND.getStatusCode());
			resourceError.setMessage(Messages.getString("MSG_DADO_NAO_ENCONTRADO"));
			resourceError.setDetail(e.getCause().toString());
			return Response.status(Response.Status.NOT_FOUND).entity(resourceError).build();
		} else if (e instanceof BadRequestException) {
			/**
			 * CODIGO 400 - BAD REQUEST
			 */
			resourceError.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resourceError.setMessage(Messages.getString("MSG_BAD_REQUEST"));
			resourceError.setDetail(e.getCause().getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(resourceError).build();
		} else if (e.toString().equals("org.jboss.resteasy.spi.UnauthorizedException")) {
			/**
			 * CODIGO 401 - UNAUTHORIZED
			 */
			resourceError.setCode(Response.Status.UNAUTHORIZED.getStatusCode());
			resourceError.setMessage(Messages.getString("MSG_USUARIO_NAO_AUTORIZADO"));
			return Response.status(Response.Status.UNAUTHORIZED).entity(resourceError).build();
		} else if (e instanceof NotAcceptableException) {
			/**
			 * CODIGO 406 - NOT ACCEPTABLE
			 */
			resourceError.setCode(Response.Status.NOT_ACCEPTABLE.getStatusCode());
			resourceError.setMessage(e.getCause().getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(resourceError).build();
		} else if (e instanceof ValidationException) {
			/**
			 * CODIGO 406 - NOT ACCEPTABLE
			 */
			resourceError.setCode(Response.Status.NOT_ACCEPTABLE.getStatusCode());
			resourceError.setMessage(e.getCause().getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(resourceError).build();
		} else if (e instanceof GroupDefinitionException) {
			/**
			 * CODIGO 406 - NOT ACCEPTABLE
			 */
			resourceError.setCode(Response.Status.NOT_ACCEPTABLE.getStatusCode());
			resourceError.setMessage(e.getCause().getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(resourceError).build();
		} else if (e instanceof ResteasyViolationException) {
			/**
			 * CODIGO 406 - NOT ACCEPTABLE
			 */
			resourceError.setCode(Response.Status.NOT_ACCEPTABLE.getStatusCode());
			resourceError.setMessage(e.getCause().getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(resourceError).build();
		} else if (e instanceof ConstraintDeclarationException) {
			/**
			 * CODIGO 406 - NOT ACCEPTABLE
			 */
			resourceError.setCode(Response.Status.NOT_ACCEPTABLE.getStatusCode());
			resourceError.setMessage(e.getCause().getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(resourceError).build();
		} else if (e instanceof RequestTimeOutException) {
			/**
			 * CODIGO 408 - REQUEST TIMEOUT
			 */
			resourceError.setCode(Response.Status.REQUEST_TIMEOUT.getStatusCode());
			resourceError.setMessage(e.getCause().getMessage());
			return Response.status(Response.Status.REQUEST_TIMEOUT).entity(resourceError).build();
		} else if (e instanceof ConflictException) {
			/**
			 * CODIGO 409 - CONFLICT
			 */
			resourceError.setCode(Response.Status.CONFLICT.getStatusCode());
			resourceError.setMessage(e.getCause().getMessage());
			return Response.status(Response.Status.CONFLICT).entity(resourceError).build();
		} else if (e.getCause() instanceof ConstraintViolationException) {
			/**
			 * INSERCAO DE VARIOS ERROS
			 */
			Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e.getCause())
					.getConstraintViolations();
			List<String> contraitsViolated = new ArrayList<String>();
			for (ConstraintViolation<?> violation : violations) {
				contraitsViolated.add(violation.getMessage());
			}
			resourceError.setCode(22);
			resourceError.setMessage(contraitsViolated.toString());
		}

		/**
		 * CODIGO 500 - Internal Server Error
		 */

		resourceError.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		if (e.getMessage() == null) {
			resourceError.setMessage(e.toString());
		} else {
			resourceError.setMessage(e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resourceError)
				.type(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_XML).build();
	}

	public Response toResponse(ValidationException exception) {
		if (exception instanceof ConstraintDefinitionException) {
			System.out.println("ConstraintDefinitionException");
		}
		if (exception instanceof ConstraintDeclarationException) {
			System.out.println("ConstraintDefinitionException");
		}
		if (exception instanceof GroupDefinitionException) {
			System.out.println("ConstraintDefinitionException");
		}
		if (exception instanceof ResteasyViolationException) {
			ResteasyViolationException resteasyViolationException = ResteasyViolationException.class.cast(exception);
			Exception e = resteasyViolationException.getException();
			if (e != null) {
				System.out.println("ConstraintDefinitionException");
			} else if (resteasyViolationException.getReturnValueViolations().size() == 0) {
				System.out.println("ConstraintDefinitionException");
			} else {
				System.out.println("ConstraintDefinitionException");
			}
		}
		System.out.println("ConstraintDefinitionException");
		return null;
	}

}