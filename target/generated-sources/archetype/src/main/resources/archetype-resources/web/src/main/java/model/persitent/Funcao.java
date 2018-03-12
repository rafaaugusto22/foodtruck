#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.persitent;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;


/**
 * The persistent class for the funcao database table.
 * 
 */
@Entity
@XmlRootElement
@NamedQuery(name="Funcao.findAll", query="SELECT f FROM Funcao f")
public class Funcao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="FUNCAO_NUFUNCAO_GENERATOR", sequenceName="SEQ_NUFUNCAO")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FUNCAO_NUFUNCAO_GENERATOR")
    private long nufuncao;

    private Boolean ativo;

    private String cofuncao;

    private String nome;

    //bi-directional many-to-one association to Empregado
    @OneToMany(mappedBy="funcao")
    private List<Empregado> empregados;

    public Funcao() {
    }

    public long getNufuncao() {
        return this.nufuncao;
    }

    public void setNufuncao(long nufuncao) {
        this.nufuncao = nufuncao;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCofuncao() {
        return this.cofuncao;
    }

    public void setCofuncao(String cofuncao) {
        this.cofuncao = cofuncao;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Empregado> getEmpregados() {
        return this.empregados;
    }

    public void setEmpregados(List<Empregado> empregados) {
        this.empregados = empregados;
    }

    public Empregado addEmpregado(Empregado empregado) {
        getEmpregados().add(empregado);
        empregado.setFuncao(this);

        return empregado;
    }

    public Empregado removeEmpregado(Empregado empregado) {
        getEmpregados().remove(empregado);
        empregado.setFuncao(null);

        return empregado;
    }

}