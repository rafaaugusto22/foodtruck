#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.mensagens;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ${package}.model.trasient.ged.tipos.Documento;

@XmlRootElement(name = "SISGD-GRAVA-DOCUMENTO")
public class MensagemGravarDocumento extends MensagemPergunta {
	
	public MensagemGravarDocumento(){
		this.getConexao().setOperacao("gravar-documento");
	}
	
	private Documento documento;
	
	@XmlElement(name="documento", namespace = "")
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}
