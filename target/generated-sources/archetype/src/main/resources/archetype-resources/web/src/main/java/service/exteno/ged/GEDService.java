#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.exteno.ged;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ${package}.model.trasient.ged.mensagens.MensagemConsultaDocumento;
import ${package}.model.trasient.ged.mensagens.MensagemExclusaoDocumento;
import ${package}.model.trasient.ged.mensagens.MensagemGravarDocumento;
import ${package}.model.trasient.ged.mensagens.MensagemRespostaConsultaDocumento;
import ${package}.model.trasient.ged.tipos.Campo;
import ${package}.model.trasient.ged.tipos.Documento;
import ${package}.model.trasient.ged.tipos.ItemGED;
import ${package}.service.exteno.jms_1_1.JMSAdapterService;
import ${package}.util.exceptions.IntegrationException;
import ${package}.util.system.Messages;

/**
 * Classe responsável por realizar chamadas ao GED. A classe abaixo estão os
 * exemplos para consulta, gravação e exclusão de documento.
 * 
 * @author c112141
 *
 */
@Stateless
public class GEDService {

	private static final Logger log = Logger.getLogger(GEDService.class.getName());

	@Inject
	JMSAdapterService jmsAdapterService;

	/**
	 * Método responsável pela consulta via GED. A consulta é realizada passando
	 * o parametro do campo "NU_SEGTO", cada sistema cadastrado no GED possui
	 * campos previamente cadastrados que podem ser diferentes do exemplo
	 * abaixo. Para o usuário consiga vizualizar os documentos é necessário que
	 * o IP do parametro "ipUsuarioTransacao" seja o mesmo da maquina do usuário
	 * que requisitou o documento.
	 * 
	 * @param idDocumento
	 * @param ipUsuarioTransacao
	 * @param usuarioTransacao
	 * @return List<ItemGED>
	 * @throws IntegrationException
	 */
	public List<ItemGED> consultarDocumeto(String idDocumento, String ipUsuarioTransacao, String usuarioTransacao)
			throws IntegrationException {

		List<ItemGED> result = null;

		try {
			log.fine("Inicia consultaDocumento");

			JAXBContext jaxbContext = JAXBContext.newInstance(MensagemConsultaDocumento.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			MensagemConsultaDocumento msg = new MensagemConsultaDocumento();
			msg.setIpUsuarioTransacao(ipUsuarioTransacao);
			msg.setUsuarioTransacao(usuarioTransacao);
			msg.addCampo(new Campo("NU_SEGTO", idDocumento));

			Writer xmlPergunta = new StringWriter();
			jaxbMarshaller.marshal(msg, xmlPergunta);

			log.info("xml built " + xmlPergunta.toString());

			String resultIntegration = jmsAdapterService.sendMessageGEDRequest(xmlPergunta.toString());

			log.info("xml response " + resultIntegration);
			if ((resultIntegration != null) && (resultIntegration.trim().length() > 0)) {

				jaxbContext = JAXBContext.newInstance(MensagemRespostaConsultaDocumento.class);

				Unmarshaller u = jaxbContext.createUnmarshaller();
				Reader xmlResposta = new StringReader(resultIntegration);

				MensagemRespostaConsultaDocumento mensagemResposta = (MensagemRespostaConsultaDocumento) u
						.unmarshal(xmlResposta);

				if (!mensagemResposta.getCodigoRetorno().equals("00")) {

					log.warning(mensagemResposta.getMensagem());

					throw new IntegrationException(Messages.getString("MSG_GED03"));
				}

				if ((mensagemResposta.getColecaoItens() == null)
						|| (mensagemResposta.getColecaoItens().getItens() == null)
						|| (mensagemResposta.getColecaoItens().getItens().size() == 0)) {

					log.warning("No itens returned");

					throw new IntegrationException(Messages.getString("MSG_GED02"));

				}
				result = mensagemResposta.getColecaoItens().getItens();
			}
		} catch (JAXBException e) {
			log.severe("Erro conversão do XML.");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Método responsável por exclusão em lote dos documentos. A exclusão é
	 * realizada passando o parametro do campo "NU_SEGTO", cada sistema
	 * cadastrado no GED possui campos previamente cadastrados que podem ser
	 * diferentes do exemplo abaixo.
	 *
	 * @param idDocumento
	 * @param ipUsuarioTransacao
	 * @param usuarioTransacao
	 * @return String
	 * @throws IntegrationException
	 */
	public String exclusaoLoteDocumeto(String idDocumento, String ipUsuarioTransacao, String usuarioTransacao)
			throws IntegrationException {

		String result = null;

		try {
			log.fine("Inicia consultaDocumento");

			JAXBContext jaxbContext = JAXBContext.newInstance(MensagemExclusaoDocumento.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			MensagemExclusaoDocumento msg = new MensagemExclusaoDocumento();
			msg.setIpUsuarioTransacao(ipUsuarioTransacao);
			msg.setUsuarioTransacao(usuarioTransacao);
			msg.getCriterioExclusao().getIndicePesquisa().addCampo(new Campo("NU_SEGTO", idDocumento));
			msg.getCriterioExclusao().setTipo("1");
			Writer xmlPergunta = new StringWriter();
			jaxbMarshaller.marshal(msg, xmlPergunta);

			log.info("xml built " + xmlPergunta.toString());

			String resultIntegration = jmsAdapterService.sendMessageGEDRequest(xmlPergunta.toString());

			log.info("xml response " + resultIntegration);
			if ((resultIntegration != null) && (resultIntegration.trim().length() > 0)) {

				jaxbContext = JAXBContext.newInstance(MensagemRespostaConsultaDocumento.class);

				Unmarshaller u = jaxbContext.createUnmarshaller();
				Reader xmlResposta = new StringReader(resultIntegration);

				MensagemRespostaConsultaDocumento mensagemResposta = (MensagemRespostaConsultaDocumento) u
						.unmarshal(xmlResposta);

				if (!mensagemResposta.getCodigoRetorno().equals("00")) {

					log.warning(mensagemResposta.getMensagem());

					throw new IntegrationException(Messages.getString("MSG_GED03"));
				}

				result = mensagemResposta.getMensagem();
			}
		} catch (JAXBException e) {
			log.severe("Erro conversão do XML.");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Classe responsável por cadastrar os documentos no GED. A inclusão é
	 * realizada passando o parametro do campo "NU_SEGTO", cada sistema
	 * cadastrado no GED possui campos previamente cadastrados que podem ser
	 * diferentes do exemplo abaixo.
	 * 
	 * @param mensagem
	 * @param tipoDocumento
	 * @param ipUsuarioTransacao
	 * @param usuarioTransacao
	 */
	public void gravaDocumento(String mensagem, String tipoDocumento, String ipUsuarioTransacao,
			String usuarioTransacao) {

		try {
			log.fine("Inicia gravaDocumento");

			JAXBContext jaxbContext = JAXBContext.newInstance(MensagemGravarDocumento.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			MensagemGravarDocumento msg = new MensagemGravarDocumento();
			msg.setIpUsuarioTransacao(ipUsuarioTransacao);
			msg.setUsuarioTransacao(usuarioTransacao);
			Documento doc = new Documento();

			doc.setExtensaoDocumento(tipoDocumento);
			doc.addCampo(new Campo("NU_SEGTO", "0001"));
			doc.setBinario(mensagem);
			msg.setDocumento(doc);

			Writer xmlPergunta = new StringWriter();
			jaxbMarshaller.marshal(msg, xmlPergunta);

			log.info("xml built " + xmlPergunta.toString());

			String resultIntegration = jmsAdapterService.sendMessageGEDGravaDocumento(xmlPergunta.toString());

			log.info("xml response " + resultIntegration);

			if ((resultIntegration != null) && (resultIntegration.trim().length() > 0)) {

				jaxbContext = JAXBContext.newInstance(MensagemRespostaConsultaDocumento.class);

				Unmarshaller u = jaxbContext.createUnmarshaller();
				Reader xmlResposta = new StringReader(resultIntegration);

				MensagemRespostaConsultaDocumento mensagemResposta = (MensagemRespostaConsultaDocumento) u
						.unmarshal(xmlResposta);

				if (!mensagemResposta.getCodigoRetorno().equals("00")) {

					log.warning(mensagemResposta.getMensagem());

					throw new IntegrationException(Messages.getString("MSG_GED03"));
				}

			}
		} catch (JAXBException e) {
			log.severe("Erro conversão do XML.");
			e.printStackTrace();
		}
	}

}
