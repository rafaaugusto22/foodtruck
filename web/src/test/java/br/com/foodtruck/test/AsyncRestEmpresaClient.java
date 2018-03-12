package br.com.foodtruck.test;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

import br.com.foodtruck.model.persitent.Empresa;

public class AsyncRestEmpresaClient {

	public static void main(String[] args) {
		new AsyncRestEmpresaClient().getEmpresaAsync();
		new AsyncRestEmpresaClient().getEmpresaAsyncCallBack();
		new AsyncRestEmpresaClient().getEmpresaAsyncTimeOut();
	}

	@Test
	public void getEmpresaAsync() {
		WebTarget target = ClientBuilder.newClient().target("http://localhost:8080/foodtruck-web/rest/async")
				.path("empresa/{id}").resolveTemplate("id", "1");

		Future<Empresa> asyncResponse = target.request().async().get(Empresa.class);

		System.out.println("STATUS DA REQUISICAO CANCELADA: " + asyncResponse.isCancelled());
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("STATUS DA REQUISICAO CONCLUIDO: " + asyncResponse.isDone());
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println("STATUS DA REQUISICAO CONCLUIDO: " + asyncResponse.isDone());

	}

	@Test
	public void getEmpresaAsyncCallBack() {

		WebTarget target = ClientBuilder.newClient().target("http://localhost:8080/foodtruck-web/rest/async")
				.path("empresa/{id}").resolveTemplate("id", "300");

		target.request().async().get(new InvocationCallback<Empresa>() {
			public void completed(Empresa empresa) {
				System.out.println("CONCLUIDA!");
			}

			public void failed(Throwable throwable) {
				System.out.println(throwable);
			}
		});

		System.out.println("Final do metodo principal...");
		try {
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getEmpresaAsyncTimeOut() {

		WebTarget target = ClientBuilder.newClient().target("http://localhost:8080/foodtruck-web/rest/async")
				.path("empresa/{id}").resolveTemplate("id", "300");

		Future<Empresa> asyncResponse = target.request().async().get(Empresa.class);

		try {
			assertTrue(asyncResponse.get(5L, TimeUnit.SECONDS).getNuempresa() == 300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

}