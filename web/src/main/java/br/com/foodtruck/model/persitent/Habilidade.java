package br.com.foodtruck.model.persitent;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the habilidade database table.
 * 
 */
@Entity
@Table(name = "Habilidade")
@XmlRootElement
@NamedQuery(name="Habilidade.findAll", query="SELECT h FROM Habilidade h")
public class Habilidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="HABILIDADE_NUHABILIDADE_GENERATOR", sequenceName="SEQ_NUHABILIDADE")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HABILIDADE_NUHABILIDADE_GENERATOR")
    private long nuhabilidade;

    private Boolean ativo;

    private String cohabilidade;

    private String nome;

    //bi-directional many-to-many association to Empregado
    @ManyToMany
    @JoinTable(
        name="empregadohabilidade"
        , joinColumns={
            @JoinColumn(name="nuhabilidade")
            }
        , inverseJoinColumns={
            @JoinColumn(name="nuempregado")
            }
        )
    private List<Empregado> empregados;

    public Habilidade() {
    }

    public long getNuhabilidade() {
        return this.nuhabilidade;
    }

    public void setNuhabilidade(long nuhabilidade) {
        this.nuhabilidade = nuhabilidade;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCohabilidade() {
        return this.cohabilidade;
    }

    public void setCohabilidade(String cohabilidade) {
        this.cohabilidade = cohabilidade;
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

}