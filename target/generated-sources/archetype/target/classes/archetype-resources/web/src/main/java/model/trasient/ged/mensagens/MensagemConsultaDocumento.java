#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.mensagens;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ${package}.model.trasient.ged.tipos.Campo;
import ${package}.model.trasient.ged.tipos.IndicePesquisa;
import ${package}.util.system.ConfigApplication;

@XmlRootElement(name = "SISGD-CONSULTA")
public class MensagemConsultaDocumento extends MensagemPergunta {

	private IndicePesquisa indicePesquisa;

	public MensagemConsultaDocumento() {
		this.getConexao().setOperacao("consulta");
		this.indicePesquisa = new IndicePesquisa();
		this.indicePesquisa.setNome(ConfigApplication.getSystemProperties(ConfigApplication.GED_INDEX_PESQUISA));

	}

	@XmlElement(name = "indice-pesquisa", namespace = "")
	public IndicePesquisa getIndicePesquisa() {
		return indicePesquisa;
	}

	public void setIndicePesquisa(IndicePesquisa indicePesquisa) {
		this.indicePesquisa = indicePesquisa;
	}

	public void addCampo(Campo campo) {
		this.indicePesquisa.addCampo(campo);

	}

}
