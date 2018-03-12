#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.mensagens;

import javax.xml.bind.annotation.XmlElement;

public class MensagemResposta {
	
	private String operacao;
	private String codigoRetorno;
	private String mensagem;
	
	@XmlElement(name="operacao", namespace="")
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	@XmlElement(name="codigo-retorno", namespace="")
	public String getCodigoRetorno() {
		return codigoRetorno;
	}
	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
	
	@XmlElement(name="mensagem", namespace="")
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
