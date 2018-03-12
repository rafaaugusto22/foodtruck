#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.mensagens;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ${package}.model.trasient.ged.tipos.Campo;
import ${package}.model.trasient.ged.tipos.CriterioExclusao;
import ${package}.model.trasient.ged.tipos.IndicePesquisa;
import ${package}.util.system.ConfigApplication;

@XmlRootElement(name = "SISGD-EXCLUSAO")
public class MensagemExclusaoDocumento extends MensagemPergunta {

	
	private CriterioExclusao criterioExclusao;
	
	

	public MensagemExclusaoDocumento() {
		this.getConexao().setOperacao("exclusao");
		IndicePesquisa indicePesquisa = new IndicePesquisa();
		this.criterioExclusao =  new CriterioExclusao();
		indicePesquisa.setNome(ConfigApplication.getSystemProperties(ConfigApplication.GED_INDEX_PESQUISA));
		this.criterioExclusao.setIndicePesquisa(indicePesquisa);
	}
	
	@XmlElement(name = "criterio-exclusao", namespace = "")
	public CriterioExclusao getCriterioExclusao() {
		return criterioExclusao;
	}

	public void setCriterioExclusao(CriterioExclusao criterioExclusao) {
		this.criterioExclusao = criterioExclusao;
	}

	

}
