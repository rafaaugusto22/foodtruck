#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class EmpresaRestClient extends AbstractTest {

	@Test
	public void getAll() {
		WebTarget target = client.target(REST_URL).path("empresa");
		Builder invocation = target.request(MediaType.APPLICATION_JSON);
		invocation.accept(MediaType.APPLICATION_JSON);
		Response response = invocation.get();
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void getById() {
		WebTarget target = client.target(REST_URL).path("/empresa/{id}").resolveTemplate("id", 1L);
		Builder invocation = target.request(MediaType.APPLICATION_JSON);
		invocation.accept(MediaType.APPLICATION_JSON);
		Response response = invocation.get();
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void pagination() {
		WebTarget target = client.target(REST_URL).path("/empresa/pagination").queryParam("inicio", 1).queryParam("fim",
				3);
		Builder invocation = target.request(MediaType.APPLICATION_JSON);
		invocation.accept(MediaType.APPLICATION_JSON);
		Response response = invocation.get();
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

}