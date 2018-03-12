#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.tipos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="campo")
public class Campo {
	
	private String nome = null;
	private String valor = null;
	
	
	public Campo() {
		
	}
	
	public Campo(String nome, String valor) {
		this.nome = nome;
		this.valor = valor;
	}
	@XmlAttribute(name="nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@XmlAttribute(name="valor")
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

}
