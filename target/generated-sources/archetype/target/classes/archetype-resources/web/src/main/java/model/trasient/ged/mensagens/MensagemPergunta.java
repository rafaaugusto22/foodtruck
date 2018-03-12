#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.mensagens;

import javax.xml.bind.annotation.XmlElement;

import ${package}.model.trasient.ged.tipos.Conexao;

public class MensagemPergunta {
	
	
	private Conexao conexao = Conexao.getInstance();

	@XmlElement(name="dados-conexao", namespace="")
	protected Conexao getConexao() {
		return conexao;
	}

	private void setConexao(Conexao conexao) {
		this.conexao = conexao;
	}
	
	public void setIpUsuarioTransacao(String ipUsuarioTransacao) {
		this.getConexao().setIpUsuarioTransacao(ipUsuarioTransacao); 
		
	}

	public void setUsuarioTransacao(String usuarioTransacao) {
		this.getConexao().setUsuarioTransacao(usuarioTransacao);
		
	}

	

}
