#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import ${package}.service.EmpresaMicroServices;

/**
 * Classe responsável por prover UPLOAD de arquivos como um recurso.
 *
 * @author 
 * @see JaxRsActivator
 * @see EmpresaMicroServices
 * @since 1.0
 * @version 1.0.0
 */
@Path("/upload")
@RequestScoped
public class UploadRESTService {

	private static final Logger log = Logger.getLogger(UploadRESTService.class.getName());

	/**
	 * Método reponsável por receber arquivos (IMAGEM e PDF) e persisti-los no
	 * GED.
	 * <p>
	 * Os campos serão mapeados pela <b>{@code tag html name}</b>.
	 * <p>
	 * <b>IMPORTANTE: </b>As tags do tipo <b>text</b> {@code 
	 * <input type="text"/>}serão ignoradas.
	 * <p>
	 * Os campos são mapeados pela {@code tag html name} do formulário.
	 *
	 * @author 
	 * @see MultipartFormDataInput
	 * @since 1.0
	 * @param multipart
	 *            /form-data
	 * @return Map no formato JSON
	 */
	@POST
	@Path("/form")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(MultipartFormDataInput input) {
		Map<String, String> retorno = new HashMap<>();
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		Set<String> keysForm = uploadForm.keySet();
		for (String chave : keysForm) {
			List<InputPart> inputParts = uploadForm.get(chave);
			for (InputPart inputPart : inputParts) {
				if (inputPart.getMediaType().getType().equalsIgnoreCase("image")
						|| inputPart.getMediaType().getType().equalsIgnoreCase("application")) {
					try {
						InputStream inputStream = inputPart.getBody(InputStream.class, null);
						log.info(chave + " - " + inputStream);
						retorno.put(chave, "sucesso");
					} catch (IOException e) {
						retorno.put(chave, "falha");
					}
				} else {
					retorno.put(chave, "ignorado");
				}
			}
		}
		return Response.status(200).entity(retorno).build();
	}

}