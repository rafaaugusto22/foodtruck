#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.ged;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import ${package}.model.trasient.ged.tipos.ItemGED;
import ${package}.service.exteno.ged.GEDService;
import ${package}.util.tools.MiscUtils;

@Path("/jms/ged")
@RequestScoped
public class GEDRESTService {
	
	@Context
	private SecurityContext sc;

	@EJB
	GEDService integracaoGED;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemGED> getDocumento(@PathParam("id") String id, @Context HttpServletRequest req) {
		String ipAddress = MiscUtils.getIpAddress(req);
		return integracaoGED.consultarDocumeto(id, ipAddress, "c112141");

	}

	@GET
	@Path("exclusao/lote/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getExclusaoLoteDocumento(@PathParam("id") String id, @Context HttpServletRequest req) {
		String ipAddress = MiscUtils.getIpAddress(req);
		return integracaoGED.exclusaoLoteDocumeto(id, ipAddress, "c112141");

	}

	/**
	 * Metodo repons�vel por receber arquivos (IMAGEM e PDF) e persisti-los no
	 * GED.
	 * <p>
	 * Os campos serao mapeados pela <b>{@code tag html name}</b>.
	 * <p>
	 * <b>IMPORTANTE: </b>As tags do tipo <b>text</b>
	 * {@code <input type="text"/>}serao ignoradas.
	 * <p>
	 * Os campos sao mapeados pela {@code tag html name} do formulario.
	 * 
	 * @author 
	 * @see MultipartFormDataInput
	 * @since 1.0
	 * @param formParams
	 * @return Map no formato JSON
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(MultipartFormDataInput input, @Context HttpServletRequest req) {

		Map<String, String> retorno = new HashMap<String, String>();

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		// CAPTURANDO TODAS AS CHAVES DOS CAMPOS DO FORMULARIO, OU SEJA, PRA
		// CADA PARAMETRO DO TIPO NAME=XXXXXXX
		Set<String> keysForm = uploadForm.keySet();

		// LOOP EXTRAINDO CADA UMA DAS CHAVES PARA TRATAR O CONTE�DO DO CAMPO
		for (String chave : keysForm) {

			// CRIANDO UMA LISTA COM TODOS OS PARAMETROS VINCULADOS AO CAMPO DO
			// FORMULARIO
			List<InputPart> inputParts = uploadForm.get(chave);

			// VERIFICANDO SE O CAMPO É DO TIPO TEXTO
			for (InputPart inputPart : inputParts) {

				// SE O CAMPO FOR DE TIPO APPLICATION "PDF" OU IMAGE
				// "JPG, GIF, PNG" A CHAVE DO CAMPO COMPOE UMA LISTA ONDE O
				// CONTEUDO DO ARQUIVO SERA EXTRAIDO.
				if (inputPart.getMediaType().getType().equalsIgnoreCase("image")
						|| inputPart.getMediaType().getType().equalsIgnoreCase("application")) {

					try {
						// UTILIZA O NOME DO ARQUIVO PARA DEFINIR A EXTENSÃO
						String ext = "";
						MultivaluedMap<String, String> headers = inputPart.getHeaders();
						String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
						for (String name : contentDispositionHeader) {
							if ((name.trim().startsWith("filename"))) {
								String[] tmp = name.split("${symbol_escape}${symbol_escape}.");
								ext = tmp[tmp.length - 1].trim().replaceAll("${symbol_escape}"", "").toUpperCase();
							}
						}
						// GERANDO O InputStream DO ARQUIVO
						InputStream inputStream = inputPart.getBody(InputStream.class, null);
						final byte[] bytes64bytes = Base64.encodeBase64(IOUtils.toByteArray(inputStream));
						final String content = new String(bytes64bytes);
						String ipAddress = MiscUtils.getIpAddress(req);
						// TENTANDO FAZER O ENVIO PARA O GED
						integracaoGED.gravaDocumento(content, ext, ipAddress, sc.getUserPrincipal().getName());
						// INCLUINDO O RESULTADO NO MAPA DE RETORNO DO METODO -
						// K=VALOR DA TAG NAME / V=SUCESSO
						retorno.put(chave, "sucesso");

					} catch (IOException e) {
						// INCLUINDO O RESULTADO NO MAPA DE RETORNO DO METODO -
						// K=VALOR DA TAG NAME / V=FALHA
						retorno.put(chave, "falha");
					}

				} else {
					// INFORMANDO QUE O CAMPO QUE NAO FOR DO TIPO APPLICATION OU
					// IMAGE FOI IGNORADO - K=VALOR DA TAG NAME / V=FALHA
					retorno.put(chave, "ignorado");
				}

			}
		}

		// RETORNO - CODIGO 200 (OK), JUNTAMENTE COM O MAPA DO TRATAMENTO.
		return Response.status(200).entity(retorno).build();

	}

}
