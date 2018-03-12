#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.tipos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="indice-pesquisa")
@XmlType(propOrder={"nome", "campos"})
public class IndicePesquisa {
	
	private String nome;
	private List<Campo> campos;
	
	@XmlElement(name="nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@XmlElementWrapper(name = "campos")
	@XmlElement(name = "campo")
	public List<Campo> getCampos() {
		return campos;
	}
	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
	
	public void addCampo(Campo novoCampo){
		if(this.campos == null){
			this.campos = new ArrayList<Campo>();
		}
		if(novoCampo != null){
			this.campos.add(novoCampo);
		}
		
	}

}
