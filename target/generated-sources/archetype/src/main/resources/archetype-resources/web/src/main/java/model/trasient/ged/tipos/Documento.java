#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.tipos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ${package}.util.system.ConfigApplication;

@XmlType(propOrder={"indicePesquisa", "extensaoDocumento","binario"})
public class Documento {

	private IndicePesquisa indicePesquisa;	
	private String extensaoDocumento;
	private String binario;
	
	public Documento() {
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
	@XmlElement(name="extensao-documento")
	public String getExtensaoDocumento() {
		return extensaoDocumento;
	}

	public void setExtensaoDocumento(String extensaoDocumento) {
		this.extensaoDocumento = extensaoDocumento;
	}
	
	@XmlElement(name="binario")
	public String getBinario() {
		return binario;
	}

	public void setBinario(String binario) {
		this.binario = binario;
	}
}
