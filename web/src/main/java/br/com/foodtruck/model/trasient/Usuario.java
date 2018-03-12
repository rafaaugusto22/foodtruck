package br.com.foodtruck.model.trasient;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {

	private String matricula;
	private String senha;
	private boolean logado = false;
	private List<String> grupos = new ArrayList<>();

	public Usuario() {
	}

	public Usuario(String matricula) {
		this.setMatricula(matricula);
	}

	@Transient
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Transient
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Transient
	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean status) {
		this.logado = status;
	}

	@Transient
	public List<String> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<String> grupos) {
		this.grupos = grupos;
	}

	@Override
	public String toString() {
		return "Usuario [matricula=" + matricula + ", senha=" + senha + ", logado=" + logado + ", grupos=" + grupos
				+ "]";
	}

}