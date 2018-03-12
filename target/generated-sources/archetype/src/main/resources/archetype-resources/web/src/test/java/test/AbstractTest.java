#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.junit.After;
import org.junit.Before;

import ${package}.util.interceptors.rest.ClientAuthenticator;

public abstract class AbstractTest {

	public static final String AUTHENTICATION = "Basic dGVzdGUyOjEyMzQ1Ng==";

	public static final String REST_URL = "http://localhost:8080/${parentArtifactId}-web/rest/";

	protected Client client;

	@Before
	public void init() {
		client = ClientBuilder.newClient();
		client.register(new ClientAuthenticator("admin", "123456"));
	}

	@After
	public void finish() {
		if (client != null) {
			client.close();
		}
	}

}