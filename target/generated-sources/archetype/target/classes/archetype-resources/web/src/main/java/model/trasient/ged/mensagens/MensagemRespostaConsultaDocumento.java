#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.mensagens;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ${package}.model.trasient.ged.tipos.ColecaoItens;

@XmlRootElement(name="SISGD-RSP")
public class MensagemRespostaConsultaDocumento extends MensagemResposta {
	

	private ColecaoItens colecaoItens;


	@XmlElement(name = "colecao-itens-recurso")
	public ColecaoItens getColecaoItens() {
		return colecaoItens;
	}

	public void setColecaoItens(ColecaoItens colecaoItens) {
		this.colecaoItens = colecaoItens;
	}

	

}
