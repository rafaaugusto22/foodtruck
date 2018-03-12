#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.tipos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"tipo", "indicePesquisa"})
public class CriterioExclusao {
	
	private IndicePesquisa indicePesquisa;
	
	private String tipo;

	@XmlElement(name = "indice-pesquisa", namespace = "")
	public IndicePesquisa getIndicePesquisa() {
		return indicePesquisa;
	}

	public void setIndicePesquisa(IndicePesquisa indicePesquisa) {
		this.indicePesquisa = indicePesquisa;
	}
	@XmlElement(name = "tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
}
