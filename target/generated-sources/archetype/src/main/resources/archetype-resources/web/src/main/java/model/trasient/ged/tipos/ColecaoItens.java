#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.trasient.ged.tipos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ColecaoItens {
	private List<ItemGED> itens;
	
	@XmlElement(name = "item", required=false)
	public List<ItemGED> getItens() {
		return itens;
	}

	public void setItens(List<ItemGED> itens) {
		this.itens = itens;
	}
	
}
