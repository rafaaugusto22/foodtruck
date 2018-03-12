#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.tipos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ${package}.util.system.ConfigApplication;

@XmlType(propOrder={"siglaSistema", "usuarioServico","senhaServico","baseCM", "repositorio","usuarioTransacao", "ipUsuarioTransacao", "operacao"})
public class Conexao {
	
	private String siglaSistema;
	private String usuarioServico;
	private String senhaServico;
	private String baseCM;
	private String repositorio;
	private String usuarioTransacao;
	private String ipUsuarioTransacao;
	private String operacao;
	
	private static Conexao conexao;
	
	
	private Conexao(){}
	
	public static Conexao getInstance(){
		if(conexao != null){
			return conexao;
		}else{
			conexao = new Conexao();
			conexao.baseCM = ConfigApplication.getSystemProperties(ConfigApplication.GED_BASE_CM);
			conexao.usuarioServico = ConfigApplication.getSystemProperties(ConfigApplication.GED_USUARIO_SERVICO);
			conexao.senhaServico =  ConfigApplication.getSystemProperties(ConfigApplication.GED_SENHA_SISTEMA);
			conexao.siglaSistema =  ConfigApplication.getSystemProperties(ConfigApplication.GED_SIGLA_SISTEMA);
			conexao.repositorio =  ConfigApplication.getSystemProperties(ConfigApplication.GED_REPOSITORIO);
			
		}
			
	    return conexao;
	}
	
	
	@XmlElement(name="sigla-sistema")
	public String getSiglaSistema() {
		return siglaSistema;
	}
	public void setSiglaSistema(String siglaSistema) {
		this.siglaSistema = siglaSistema;
	}
	@XmlElement(name="usuario-servico")
	public String getUsuarioServico() {
		return usuarioServico;
	}
	public void setUsuarioServico(String usuarioServico) {
		this.usuarioServico = usuarioServico;
	}
	@XmlElement(name="senha-servico")
	public String getSenhaServico() {
		return senhaServico;
	}
	public void setSenhaServico(String senhaServico) {
		this.senhaServico = senhaServico;
	}
	@XmlElement(name="base-cm")
	public String getBaseCM() {
		return baseCM;
	}
	public void setBaseCM(String baseCM) {
		this.baseCM = baseCM;
	}
	@XmlElement(name="repositorio")
	public String getRepositorio() {
		return repositorio;
	}
	public void setRepositorio(String repositorio) {
		this.repositorio = repositorio;
	}
	@XmlElement(name="usuario-transacional")
	public String getUsuarioTransacao() {
		return usuarioTransacao;
	}
	public void setUsuarioTransacao(String usuarioTransacao) {
		this.usuarioTransacao = usuarioTransacao;
	}
	@XmlElement(name="ip-usuario-transacional")
	public String getIpUsuarioTransacao() {
		return ipUsuarioTransacao;
	}
	public void setIpUsuarioTransacao(String ipUsuarioTransacao) {
		this.ipUsuarioTransacao = ipUsuarioTransacao;
	}
	@XmlElement(name="operacao")
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	

}
