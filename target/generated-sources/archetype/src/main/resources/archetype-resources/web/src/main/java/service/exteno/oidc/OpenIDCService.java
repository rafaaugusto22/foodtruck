#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.oidc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import ${package}.service.exteno.jms_2_0.JMSLocalConsumerMDBService;
import ${package}.util.system.ConfigApplication;

/**
 * Classe responsável por interagir com o IdP (Identity Provider), a interação entre o cliente 
 * e Identity provider é baseado na especificação do OpenID Connect.  
 * 
 * @author c112141
 * @since 1.0
 * @version 1.0
 *
 */
public class OpenIDCService {
	
	private static final Logger log = Logger.getLogger(OpenIDCService.class.getName());

	/**
	 * Método responsável por recuperar o access_token e o id_token.
	 * O access_token é reponsável por conter as informações de autorização e o id_token é responsável 
	 * pelas as informações de autenticação.  
	 * @param req
	 * @param code
	 * @return Response
	 * @throws IOException
	 */
	public String getToken(HttpServletRequest req, String code) throws IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ConfigApplication.getSystemProperties(ConfigApplication.OIDC_URL_TOKEN));

		Form form = new Form();
		form.param("code", code);
		form.param("grant_type", "authorization_code");
		form.param("redirect_uri", req.getRequestURL().toString().replace(req.getRequestURI().toString(), "")
				+ req.getContextPath() + "/");
		form.param("client_id", ConfigApplication.getSystemProperties(ConfigApplication.OIDC_CLIENT));
		form.param("client_secret", ConfigApplication.getSystemProperties(ConfigApplication.OIDC_SECRET));

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		return response.readEntity(String.class);
	}

	/**
	 * Método responsável por validar a assinatura do access_token e 
	 * retornar as informações relevantes do token
	 * 
	 * @param accessToken
	 * @return Response
	 * @throws Exception
	 */
	public JWTClaimsSet validate(String accessToken) throws Exception {
		try {
			ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<SecurityContext>();

			JWKSource<SecurityContext> keySource = new RemoteJWKSet<SecurityContext>(
					new URL(ConfigApplication.getSystemProperties(ConfigApplication.OIDC_URL_CERTS)));

			JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;

			JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<SecurityContext>(
					expectedJWSAlg, keySource);
			jwtProcessor.setJWSKeySelector(keySelector);

			SecurityContext ctx = null;
			JWTClaimsSet claimsSet = jwtProcessor.process(accessToken, ctx);

			return claimsSet;

		} catch (MalformedURLException e) {
			// Erro na Url informada
			e.printStackTrace();
			log.severe("Erro na URL informada");
			throw e;
		} catch (ParseException e) {
			// Erro na conversão de tipos
			e.printStackTrace();
			log.severe("Erro na conversão do token");
			throw e;
		} catch (BadJOSEException e) {
			// Assinatura invalida
			e.printStackTrace();
			log.severe("Assinatura inválida");
			throw e;

		} catch (JOSEException e) {
			// Erro de processamento
			e.printStackTrace();
			log.severe("Erro de processamento do token");
			throw e;
		}

	}

	/**
	 * Método responsável por verificar se o access_token está ativo no IdP (Identidy Provider).
	 * 
	 * @param accessToken
	 * @return Response
	 * @throws IOException
	 */
	public boolean isActiveToken(String accessToken) throws IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ConfigApplication.getSystemProperties(ConfigApplication.OIDC_URL_INTROSPECCT));

		Form form = new Form();
		form.param("client_id", ConfigApplication.getSystemProperties(ConfigApplication.OIDC_CLIENT));
		form.param("client_secret", ConfigApplication.getSystemProperties(ConfigApplication.OIDC_SECRET));
		form.param("token", accessToken);

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));
		return jsonResponse.getBoolean("active");
	}

	/**
	 * Método que realiza logout. 
	 * @param idTokenString
	 * @return Response
	 */
	public Response logout(String idTokenString) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client
				.target(ConfigApplication.getSystemProperties(ConfigApplication.OIDC_URL_LOGOUT)
						+ "?id_token_hint={idToken}")
				.resolveTemplate("idToken", idTokenString).queryParam("verbose", true);

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		return response;
	}

}
