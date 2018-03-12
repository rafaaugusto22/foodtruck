#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
 package ${package}.model.trasient.ged.tipos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class ItemGED {
	
	private List<Campo> campos;
	private String  PID;
	private String  link;
	private boolean	isPasta;
	private String  numeroVersao;
	private String  nomeItemType;
	
	@XmlElementWrapper(name = "campos")
	@XmlElement(name = "campo")
	public List<Campo> getCampos() {
		return campos;
	}
	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
	
	@XmlElement(name = "pid")
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	@XmlElement(name = "link")
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@XmlElement(name = "pasta")
	public boolean isPasta() {
		return isPasta;
	}
	public void setPasta(boolean isPasta) {
		this.isPasta = isPasta;
	}
	@XmlElement(name = "versao")
	public String getNumeroVersao() {
		return numeroVersao;
	}
	public void setNumeroVersao(String numeroVersao) {
		this.numeroVersao = numeroVersao;
	}
	@XmlElement(name = "item-type")
	public String getNomeItemType() {
		return nomeItemType;
	}
	public void setNomeItemType(String nomeItemType) {
		this.nomeItemType = nomeItemType;
	}

}
