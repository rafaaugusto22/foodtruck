package br.com.foodtruck.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractTest {

	public static final String AUTHENTICATION = "Basic dGVzdGUyOjEyMzQ1Ng==";

	public static final String REST_URL = "http://localhost:8080/foodtruck-web/rest/";

	protected Client client;

	@Before
	public void init() {
		client = ClientBuilder.newClient();
	}

	@After
	public void finish() {
		if (client != null) {
			client.close();
		}
	}

}